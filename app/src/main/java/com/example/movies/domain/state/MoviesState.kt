package com.example.movies.domain.state

import com.example.movies.domain.model.Movie

sealed class MoviesState {

    object Initial : MoviesState()

    object Loading : MoviesState()

    data class Movies(val movies: List<Movie>) : MoviesState()
}