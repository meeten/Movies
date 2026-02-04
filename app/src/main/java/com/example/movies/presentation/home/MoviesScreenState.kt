package com.example.movies.presentation.home

import com.example.movies.domain.model.BaseMovie

sealed class MoviesScreenState {

    object Initial : MoviesScreenState()

    object Loading : MoviesScreenState()

    data class Error(val error: Exception) : MoviesScreenState()

    data class Movies(
        val movies: List<BaseMovie>,
        val isLoadingNextMovies: Boolean = false
    ) : MoviesScreenState()
}