package com.example.movies.navigation

import android.net.Uri
import com.example.movies.domain.model.Movie
import com.google.gson.Gson

sealed class Screen(val route: String) {

    object Film : Screen(FILM_ROUTE) {

        private const val FILM_ROUTE_WITH_ARGS = "film"

        fun createRoute(movie: Movie): String {
            val movieGson = Gson().toJson(movie)
            return "$FILM_ROUTE_WITH_ARGS/${movieGson.encode()}"
        }
    }

    object Home : Screen(HOME_ROUTE)
    object FavoriteMovies : Screen(FAVORITE_MOVIES_ROUTE)
    object Movies : Screen(MOVIES_ROUTE)

    companion object {
        const val KEY_MOVIE = "movie"

        const val HOME_ROUTE = "home"
        const val FAVORITE_MOVIES_ROUTE = "favorite_movies"
        const val MOVIES_ROUTE = "movies"
        const val FILM_ROUTE = "film/{$KEY_MOVIE}"
    }

    fun String.encode(): String {
        return Uri.encode(this)
    }
}