package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.station

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.R
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.databinding.FragmentStationBinding
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Distance
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.SpaceStation
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Spaceship
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.adapter.ViewPagerAdapter
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.callback.FilterErrorCallback
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.callback.TimerCallback
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.callback.ViewPagerCallback
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util.Constants.TAG
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util.CustomTimer
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StationFragment : Fragment(), SearchView.OnQueryTextListener, FilterErrorCallback,
    ViewPagerCallback, TimerCallback {

    private var toast: Toast? = null
    private var alertDialog: AlertDialog? = null
    private val stationViewModel: StationViewModel by viewModels()

    private lateinit var binding: FragmentStationBinding
    private lateinit var spaceship: Spaceship
    private lateinit var stations: List<SpaceStation>
    private var timer: CustomTimer? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStationBinding.inflate(inflater, container, false)

        initSearchView()

        stationViewModel.spaceStations.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> Log.d(TAG, "spaceStations: Loading...")
                is State.Error -> {
                    Log.e(TAG, "spaceStations: Error", it.exception)
                    showAlert("Beklenmedik hata oluştu! Uygulama kapanacak.") { _, _ ->
                        requireActivity().finish()
                    }
                }
                is State.Success -> handleStations(it.data)
            }
        })

        stationViewModel.spaceship.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> Log.d(TAG, "spaceship: Loading...")
                is State.Error -> {
                    Log.e(TAG, "spaceship: Error", it.exception)
                }
                is State.Success -> handleSpaceship(it.data)
            }
        })

        stationViewModel.getSpaceship()
        stationViewModel.getSpaceStations(false)

        return binding.root
    }

    private fun initSearchView() {
        binding.searchBar.setOnQueryTextListener(this)
    }

    private fun initViewPager() {
        viewPagerAdapter = ViewPagerAdapter(this, this, spaceship)
        binding.viewPager.adapter = viewPagerAdapter
    }

    private fun handleSpaceship(data: Spaceship) {
        binding.spaceship = data
        spaceship = data
        if (viewPagerAdapter == null) {
            initViewPager()
        } else {
            viewPagerAdapter!!.updateSpaceship(spaceship)
        }

        if (timer == null) initTimer()
    }

    private fun initTimer() {
        timer = CustomTimer(spaceship, this)
        timer!!.start()
    }

    private fun handleStations(data: List<SpaceStation>) {
        if (spaceship.currentStation == null) {
            spaceship.currentStation = data[0]
            spaceship.currentStation!!.isVisited = true
            stationViewModel.updateSpaceshipSync(spaceship)
        } else if (!spaceship.currentStation!!.isVisited) {
            spaceship.currentStation!!.isVisited = true
            stationViewModel.updateSpaceshipSync(spaceship)
        }

        setViewPagerItems(data)
    }

    private fun setViewPagerItems(data: List<SpaceStation>) {
        this.stations = data
        viewPagerAdapter!!.items = data
        viewPagerAdapter!!.allItems = data
        viewPagerAdapter!!.removeFirstItem()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return if (!newText.isNullOrEmpty() && newText.length >= 3) {
            viewPagerAdapter!!.filter.filter(newText)
            true
        } else {
            viewPagerAdapter!!.items = viewPagerAdapter!!.allItems
            false
        }
    }

    override fun onFilterError() {
        showToast("Üzgünüz, aradığınız istasyon bulunamadı!")
    }

    override fun onFavoriteButtonClick(position: Int): Boolean {
        val spaceStation = viewPagerAdapter!!.items[position]
        val isUpdateSuccessful =
            stationViewModel.favoriteSpaceStation(viewPagerAdapter!!.items[position])
        if (!isUpdateSuccessful) {
            showToast("Uzay İstasyonu Güncellenemedi!")
        } else {
            spaceStation.isFavorite = !spaceStation.isFavorite
            viewPagerAdapter!!.updateSpaceStation(position, spaceStation)
        }
        return isUpdateSuccessful
    }

    override fun onTravelButtonClick(position: Int) {
        val oldSpaceship = Spaceship(spaceship)
        val selectedSpaceStation = viewPagerAdapter!!.items[position]
        val distance = Distance(
            spaceship.currentStation!!.coordinate,
            selectedSpaceStation.coordinate
        )

        if (validateDataBeforeTravel(selectedSpaceStation, distance)) {
            travel(position, oldSpaceship, selectedSpaceStation, distance, false)
        }
    }

    private fun validateDataBeforeTravel(
        selectedSpaceStation: SpaceStation,
        distance: Distance
    ): Boolean {
        when {
            spaceship.hp <= 0 || spaceship.ugs <= 0 || spaceship.eus <= 0 -> checkSpaceshipForTravel()
            selectedSpaceStation.need > spaceship.ugs -> showToast("Yetersiz UGS! Bu istasyona seyahat edilemez!")
            distance.value > spaceship.eus -> showToast("Yetersiz EUS! Bu istasyona seyahat edilemez!")
            else -> return true
        }

        return false
    }

    private fun travel(
        position: Int,
        oldSpaceship: Spaceship,
        selectedSpaceStation: SpaceStation,
        distance: Distance,
        reset: Boolean
    ) {
        spaceship.currentStation = selectedSpaceStation
        updateSpaceshipValues(spaceship, distance, selectedSpaceStation)
        val isUpdateSuccessful = stationViewModel.updateSpaceshipSync(spaceship)
        if (isUpdateSuccessful) {
            updateSpaceStation(position, selectedSpaceStation, reset)
        } else {
            spaceship = oldSpaceship
            showToast("Seyahat başarısız! Görev tamamlanamadı!")
        }
    }

    private fun updateSpaceStation(
        position: Int,
        selectedSpaceStation: SpaceStation,
        reset: Boolean
    ) {
        selectedSpaceStation.stock = selectedSpaceStation.stock + selectedSpaceStation.need
        selectedSpaceStation.need = 0
        selectedSpaceStation.isVisited = true
        stationViewModel.updateSpaceStation(selectedSpaceStation)
        viewPagerAdapter!!.updateSpaceStation(position, selectedSpaceStation)
        handleSpaceship(spaceship)
        var b = true
        if (selectedSpaceStation.id != 1) {
            b = checkSpaceshipForTravel()
        } else {
            spaceship.timer = (spaceship.durability * 10000) / 1000
            spaceship.hp = 100
            stationViewModel.updateSpaceship(spaceship)
            stationViewModel.getSpaceStations(reset)
        }

        if (b) {
            showTravelSuccessMessage(selectedSpaceStation)
        }
        binding.viewPager.setCurrentItem(0, true)
    }

    private fun updateSpaceshipValues(
        spaceship: Spaceship,
        distance: Distance,
        selectedSpaceStation: SpaceStation
    ) {
        spaceship.eus =
            if (selectedSpaceStation.id == 1) spaceship.speed * 20 else spaceship.eus - distance.value
        spaceship.ugs =
            if (selectedSpaceStation.id == 1) spaceship.capacity * 10000 else spaceship.ugs - selectedSpaceStation.need
        spaceship.ds =
            if (selectedSpaceStation.id == 1) spaceship.durability * 10000 else spaceship.ds

    }

    private fun checkSpaceshipForTravel(): Boolean {
        when {
            spaceship.hp == 0 -> showAlertToReturnToEarth(
                "Araç hasarlı!",
                false
            )
            spaceship.eus == 0L -> showAlertToReturnToEarth("Zamanınız bitti!", false)
            spaceship.ugs == 0L -> showAlertToReturnToEarth("Malzemeler tükendi!", false)
            allMissionsAreCompleted() -> showAlertToReturnToEarth(
                "Tüm görevleriniz tamamlandı!",
                true
            )
            else -> return true
        }

        return false
    }

    private fun allMissionsAreCompleted(): Boolean {
        stations.map {
            if (!it.isVisited) {
                return false
            }
        }

        return true
    }

    private fun showTravelSuccessMessage(selectedSpaceStation: SpaceStation) {
        if (selectedSpaceStation.id == 1) {
            showToast("Dünyaya geri döndünüz! Görevinizi tamamlayabilirsiniz!")
        } else {
            showToast("Seyahat başarılı! Görev tamamlandı!")
        }
    }

    private fun showToast(msg: String) {
        toast?.cancel()
        toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
        toast!!.show()
    }

    private fun showAlertToReturnToEarth(msg: String, reset: Boolean) {
        showAlert(if (reset) msg else "$msg\nKalan görevleri tamamlamak için Dünyaya geri dönmelisiniz!") { _, _ ->
            travel(0, spaceship, stations[0], Distance(), reset)
        }
    }

    private fun showAlert(msg: String, onClickListener: DialogInterface.OnClickListener) {
        stopTimer()
        alertDialog?.dismiss()
        alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Uyarı")
            .setCancelable(false)
            .setMessage(msg)
            .setIcon(R.drawable.ic_alert)
            .setNegativeButton("Tamam", onClickListener)
            .create()
        alertDialog!!.show()
    }

    private fun stopTimer() {
        spaceship.ds = spaceship.durability * 10000
        spaceship.timer = spaceship.ds / 1000
        timer?.cancel()
        timer = null
    }

    override fun onTick(millisUntilFinished: Long) {
        spaceship.timer = millisUntilFinished / 1000
        spaceship.ds = spaceship.timer * 1000
        stationViewModel.updateSpaceship(spaceship)
    }

    override fun onFinish() {
        if (checkSpaceshipForTravel()) {
            spaceship.hp -= 10
            spaceship.timer = (spaceship.durability * 10000) / 1000
            stationViewModel.updateSpaceship(spaceship)
            timer!!.start()
        } else {
            stopTimer()
        }
    }
}