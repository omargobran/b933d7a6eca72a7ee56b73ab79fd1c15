package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.favorites

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
class FavoritesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _favoriteSpaceStations = MutableLiveData<State<List<SpaceStation>>>()
    val favoriteSpaceStations: LiveData<State<List<SpaceStation>>> = _favoriteSpaceStations

    private val _spaceship = MutableLiveData<State<Spaceship>>()
    val spaceship: LiveData<State<Spaceship>> = _spaceship

    fun getFavoriteSpaceStations() {
        viewModelScope.launch {
            repository.getFavoriteSpaceStations().onEach {
                _favoriteSpaceStations.value = it
            }.launchIn(this)
        }
    }

    fun favoriteSpaceStation(spaceStation: SpaceStation): Boolean =
        repository.favoriteSpaceStationSync(spaceStation, !spaceStation.isFavorite)

    fun getSpaceship() {
        viewModelScope.launch {
            repository.getSpaceship().onEach {
                _spaceship.value = it
            }.launchIn(this)
        }
    }
}