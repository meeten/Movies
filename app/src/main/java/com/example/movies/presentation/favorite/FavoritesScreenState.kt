package com.example.movies.presentation.favorite

import com.example.movies.domain.model.MovieDetail

sealed class FavoritesScreenState {

    object Initial : FavoritesScreenState()
    object Loading : FavoritesScreenState()
    data class FavoriteMovies(val movies: List<MovieDetail>) : FavoritesScreenState()
}