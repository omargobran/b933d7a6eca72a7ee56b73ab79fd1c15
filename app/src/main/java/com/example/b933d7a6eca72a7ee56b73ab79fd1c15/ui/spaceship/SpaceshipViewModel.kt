package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.spaceship

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Spaceship
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.repository.Repository
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util.Constants.TAG
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpaceshipViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private var _spaceship: Spaceship? = null
    val spaceship get() = _spaceship!!

    private val _spaceshipLiveData: MutableLiveData<State<Spaceship>> = MutableLiveData()
    val spaceshipLiveData: LiveData<State<Spaceship>> get() = _spaceshipLiveData

    private val _spaceshipInsertionLiveData: MutableLiveData<State<Boolean>> = MutableLiveData()
    val spaceshipInsertionLiveData: LiveData<State<Boolean>> get() = _spaceshipInsertionLiveData

    var oldDurabilityValue = 0
    var oldSpeedValue = 0
    var oldCapacityValue = 0

    fun getSpaceshipFromCache() {
        viewModelScope.launch {
            repository.getSpaceship().onEach {
                _spaceshipLiveData.value = it
            }.launchIn(this)
        }
    }

    fun addSpaceship(spaceship: Spaceship) {
        viewModelScope.launch {
            repository.addSpaceship(spaceship).onEach {
                _spaceshipInsertionLiveData.value = it
            }.launchIn(this)
        }
    }
}