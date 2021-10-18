package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.spaceship

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.databinding.FragmentSpaceshipBinding
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Spaceship
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.MainActivity
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util.Constants.TAG
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util.State
import com.google.android.material.slider.Slider
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class SpaceshipFragment : Fragment(), View.OnClickListener, Slider.OnChangeListener {

    companion object {
        @JvmStatic
        fun newInstance() = SpaceshipFragment()
    }

    private val spaceshipViewModel: SpaceshipViewModel by viewModels()

    private lateinit var binding: FragmentSpaceshipBinding
    private lateinit var toast: Toast

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpaceshipBinding.inflate(inflater, container, false)

        toast = Toast.makeText(context, "Puan Kalmadı", Toast.LENGTH_SHORT)

        spaceshipViewModel.spaceshipLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is State.Error -> Log.d(TAG, "onCreateView: spaceshipLiveData Error")
                is State.Success -> goToMain()
            }
        })

        spaceshipViewModel.spaceshipInsertionLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is State.Error -> {
                    Log.e(TAG, "onCreateView: spaceshipInsertionLiveData Error", it.exception)
                    handleInsertion(false)
                }
                is State.Success -> handleInsertion(it.data)
            }
        })

        spaceshipViewModel.getSpaceshipFromCache()
        initListeners()

        return binding.root
    }

    private fun initListeners() {
        binding.confirmButton.setOnClickListener(this)
        binding.durabilitySeekBar.addOnChangeListener(this)
        binding.speedSeekBar.addOnChangeListener(this)
        binding.capacitySeekBar.addOnChangeListener(this)
    }

    private fun handleInsertion(isSuccess: Boolean) {
        if (isSuccess) {
            goToMain()
        } else {
            Toast.makeText(context, "Uzay Aracı Eklenemedi!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(v: View?) {
        if (v == binding.confirmButton) {
            handleConfirmBtnClick()
        }
    }

    private fun handleConfirmBtnClick() {
        if (validateViews()) {
            spaceshipViewModel.addSpaceship(
                Spaceship(
                    1,
                    binding.spaceshipNameEditText.text.toString(),
                    binding.durabilitySeekBar.value.toLong(),
                    binding.speedSeekBar.value.toLong(),
                    binding.capacitySeekBar.value.toLong(),
                    binding.durabilitySeekBar.value.toLong() * 10000,
                    binding.speedSeekBar.value.toLong() * 20,
                    binding.capacitySeekBar.value.toLong() * 10000,
                    100,
                    (binding.durabilitySeekBar.value.toLong() * 10000) / 1000,
                    null
                )
            )
        }
    }

    private fun validateViews(): Boolean {
        if (binding.spaceshipNameEditText.text.isNullOrEmpty()
            || binding.durabilitySeekBar.value.equals(0.0f)
            || binding.speedSeekBar.value.equals(0.0f)
            || binding.capacitySeekBar.value.equals(0.0f)
        ) {
            Toast.makeText(context, "İlgili alan boş bırakılamaz!", Toast.LENGTH_LONG)
                .show()
            return false
        }

        return true
    }

    override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
        val currentValue = value.roundToInt()
        var sliderValueDiff = 0
        var oldValue = 0
        when (slider) {
            binding.durabilitySeekBar -> {
                sliderValueDiff = currentValue - spaceshipViewModel.oldDurabilityValue
                oldValue = spaceshipViewModel.oldDurabilityValue
            }
            binding.speedSeekBar -> {
                sliderValueDiff = currentValue - spaceshipViewModel.oldSpeedValue
                oldValue = spaceshipViewModel.oldSpeedValue
            }
            binding.capacitySeekBar -> {
                sliderValueDiff = currentValue - spaceshipViewModel.oldCapacityValue
                oldValue = spaceshipViewModel.oldCapacityValue
            }
        }

        val points = binding.points.text.toString().toInt()
        if ((points > 0 && points >= sliderValueDiff) || (points == 0 && sliderValueDiff < 0)) {
            updateTotalPoints(sliderValueDiff, points)
            updateSliderValues(slider, currentValue)
        } else {
            toast.cancel()
            toast.show()
            slider.removeOnChangeListener(this)
            slider.value = oldValue.toFloat()
            slider.addOnChangeListener(this)
        }
    }

    private fun updateSliderValues(slider: Slider, currentValue: Int) {
        when (slider) {
            binding.durabilitySeekBar -> {
                spaceshipViewModel.oldDurabilityValue = currentValue
            }
            binding.speedSeekBar -> {
                spaceshipViewModel.oldSpeedValue = currentValue
            }
            binding.capacitySeekBar -> {
                spaceshipViewModel.oldCapacityValue = currentValue
            }
        }
    }

    private fun updateTotalPoints(sliderValueDiff: Int, points: Int) {
        binding.points.text = (points - sliderValueDiff).toString()
    }

    private fun goToMain() {
        startActivity(Intent(context, MainActivity::class.java))
        requireActivity().finish()
    }
}