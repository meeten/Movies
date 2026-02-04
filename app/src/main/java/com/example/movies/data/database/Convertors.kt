package com.example.movies.data.database

import androidx.room.TypeConverter
import com.example.movies.domain.entity.Trailer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Convertors {

    val gson = Gson()

    @TypeConverter
    fun toJson(trailers: List<Trailer>): String {
        return gson.toJson(trailers)
    }

    @TypeConverter
    fun fromJson(json: String): List<Trailer> {
        val type = object : TypeToken<List<Trailer>>() {}.type
        return gson.fromJson(json, type)
    }
}