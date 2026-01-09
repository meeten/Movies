package com.example.movies.domain.state

import com.example.movies.domain.model.BaseMovie

sealed class MoviesState {

    object Initial : MoviesState()

    object Loading : MoviesState()

    data class Error(val error: Exception) : MoviesState()

    data class Movies(
        val movies: List<BaseMovie>,
        val isLoadingNextMovies: Boolean = false
    ) : MoviesState()
}