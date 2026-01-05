package com.example.movies.domain.state

import com.example.movies.domain.model.MoviePreview

sealed class MoviesState {

    object Initial : MoviesState()

    object Loading : MoviesState()

    data class Movies(
        val movies: List<MoviePreview>,
        val isLoadingNextMovies: Boolean = false
    ) : MoviesState()
}