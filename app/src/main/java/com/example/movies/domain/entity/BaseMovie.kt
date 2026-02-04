package com.example.movies.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

sealed interface BaseMovie {
    val id: Int
    val posterUrl: String
    val rating: String
}

data class MoviePreview(
    override val id: Int,
    override val posterUrl: String,
    override val rating: String,
) : BaseMovie

data class MovieDetail(
    override val id: Int,
    override val rating: String,
    override val posterUrl: String,
    val name: String,
    val year: Int,
    val description: String,
    val trailers: List<Trailer>,
    val isFavorite: Boolean = false
) : BaseMovie