package com.example.movies.domain.model

data class Movie(
    val id: Int,
    val name: String,
    val year: Int,
    val description: String,
    val poster: String,
    val trailers: List<Trailer>
)