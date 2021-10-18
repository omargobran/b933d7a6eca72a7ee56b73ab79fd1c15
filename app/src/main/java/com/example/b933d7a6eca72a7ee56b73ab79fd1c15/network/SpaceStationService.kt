package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.network

import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.network.model.SpaceStationDto
import retrofit2.http.GET

interface SpaceStationService {
    @GET("e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
    suspend fun getSpaceStations(): List<SpaceStationDto>
}