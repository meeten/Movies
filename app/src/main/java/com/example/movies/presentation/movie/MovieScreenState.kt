package com.example.movies.presentation.movie

import com.example.movies.domain.model.MovieDetail

sealed class MovieScreenState {
    object Initial : MovieScreenState()
    object Loading : MovieScreenState()
    data class Movie(val movie: MovieDetail) : MovieScreenState()
}