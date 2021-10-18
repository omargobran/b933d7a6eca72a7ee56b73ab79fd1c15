package com.example.b933d7a6eca72a7ee56b73ab79fd1c15

import android.app.Application
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.repository.Repository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class SpaceApp : Application() {

    @Inject
    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            repository.getSpaceStations(false).launchIn(this)
        }
    }
}