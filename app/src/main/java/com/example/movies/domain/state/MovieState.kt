package com.example.movies.domain.state

sealed class MovieState {
    object Initial : MovieState()
    object Loading : MovieState()
    data class Movie(val movie: com.example.movies.domain.model.Movie) : MovieState()
}