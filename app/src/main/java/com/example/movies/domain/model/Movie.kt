package com.example.movies.domain.model

data class Movie(
    val id: Int = 0,
    val name: String,
    val rating: String,
    val year: String,
    val description: String,
    val poster: Poster
)
