package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.station

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.SpaceStation
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Spaceship
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.repository.Repository
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StationViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _spaceStations = MutableLiveData<State<List<SpaceStation>>>()
    val spaceStations: LiveData<State<List<SpaceStation>>> = _spaceStations

    private val _spaceship = MutableLiveData<State<Spaceship>>()
    val spaceship: LiveData<State<Spaceship>> = _spaceship

    fun getSpaceStations(reset: Boolean) {
        viewModelScope.launch {
            if (!reset) {
                repository.getSpaceStationsFromCache().onEach {
                    _spaceStations.value = it
                }.launchIn(this)
            } else {
                repository.getSpaceStations(true).onEach {
                    _spaceStations.value = it
                }.launchIn(this)
            }
        }
    }

    fun getSpaceship() {
        viewModelScope.launch {
            repository.getSpaceship().onEach {
                _spaceship.value = it
            }.launchIn(this)
        }
    }

    fun updateSpaceshipSync(spaceship: Spaceship) =
        repository.updateSpaceshipSync(spaceship)

    fun updateSpaceship(spaceship: Spaceship) {
        viewModelScope.launch {
            repository.updateSpaceship(spaceship).onEach {
                _spaceship.value = it
            }.launchIn(this)
        }
    }

    fun updateSpaceStation(spaceStation: SpaceStation) =
        repository.updateSpaceStationSync(spaceStation)

    fun favoriteSpaceStation(spaceStation: SpaceStation): Boolean =
        repository.favoriteSpaceStationSync(spaceStation, !spaceStation.isFavorite)

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["android:enabled"])
        fun setViewEnabled(view: View, isEnabled: Boolean) {
            view.alpha = if (!isEnabled) 0.6f else 1f
            view.isEnabled = isEnabled
            checkForChildren(view, isEnabled)
        }

        private fun checkForChildren(view: View, isEnabled: Boolean) {
            if (view is ViewGroup) {
                for (i in 0..view.childCount) {
                    val childView = view.getChildAt(i)
//                    if (childView is ViewGroup) {
//                        checkForChildren(childView, isEnabled)
//                    }
                    childView?.let {
                        it.isEnabled = isEnabled
                    }
                }
            }
        }
    }
}