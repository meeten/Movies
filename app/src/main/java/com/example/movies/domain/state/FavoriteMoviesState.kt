package com.example.movies.domain.state

import com.example.movies.domain.model.MovieDetail

sealed class FavoriteMoviesState {

    object Initial : FavoriteMoviesState()
    object Loading : FavoriteMoviesState()
    data class FavoriteMovies(val movies: List<MovieDetail>) : FavoriteMoviesState()
}