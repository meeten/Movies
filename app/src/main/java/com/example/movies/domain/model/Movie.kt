package com.example.movies.domain.model

data class Movie(
    val id: Int,
    val name: String,
    val rating: String,
    val year: String,
    val poster: Poster
)
