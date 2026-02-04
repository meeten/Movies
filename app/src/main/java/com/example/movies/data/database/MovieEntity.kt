package com.example.movies.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.domain.entity.Trailer

@Entity(tableName = "tableMovie")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val rating: String,
    val posterUrl: String,
    val name: String,
    val year: Int,
    val description: String,
    val trailers: List<Trailer>,
    val isFavorite: Boolean = false
)
