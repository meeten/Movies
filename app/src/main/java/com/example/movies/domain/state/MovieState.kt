package com.example.movies.domain.state

import com.example.movies.domain.model.MovieDetail

sealed class MovieState {
    object Initial : MovieState()
    object Loading : MovieState()
    data class Movie(val movie: MovieDetail) : MovieState()
}