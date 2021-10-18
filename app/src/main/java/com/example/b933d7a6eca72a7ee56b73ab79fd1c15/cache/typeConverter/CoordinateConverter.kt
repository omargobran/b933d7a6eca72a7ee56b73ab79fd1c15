package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.typeConverter

import androidx.room.TypeConverter
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Coordinate
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class CoordinateConverter : BaseTypeConverter<Coordinate>() {

    @TypeConverter
    override fun fromString(string: String): Coordinate {
        val gson: Gson = GsonBuilder().create()
        return gson.fromJson(string, Coordinate::class.java)
    }

    @TypeConverter
    override fun toString(value: Coordinate): String {
        val gson: Gson = GsonBuilder().create()
        return gson.toJson(value)
    }

}