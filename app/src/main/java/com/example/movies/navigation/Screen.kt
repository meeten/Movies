package com.example.movies.navigation

sealed class Screen(val route: String) {

    object Film : Screen(FILM_ROUTE) {

        private const val FILM_ROUTE_WITH_ARGS = "film"

        fun createRoute(movieId: Int): String {
            return "$FILM_ROUTE_WITH_ARGS/$movieId"
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
}