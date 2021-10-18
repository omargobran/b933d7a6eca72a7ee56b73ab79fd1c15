package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.typeConverter

import androidx.room.TypeConverter
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Coordinate
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.SpaceStation
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class SpaceStationConverter : BaseTypeConverter<SpaceStation?>() {

    @TypeConverter
    override fun fromString(string: String): SpaceStation? {
        val gson: Gson = GsonBuilder().create()
        return gson.fromJson(string, SpaceStation::class.java)
    }

    @TypeConverter
    override fun toString(value: SpaceStation?): String {
        val gson: Gson = GsonBuilder().create()
        return gson.toJson(value)
    }

}