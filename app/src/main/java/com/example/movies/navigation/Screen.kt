package com.example.movies.navigation

sealed class Screen(val route: String) {

    object Home : Screen(HOME_ROUTE)
    object Movies : Screen(MOVIES_ROUTE)
    object MovieDetail : Screen(MOVIE_DETAIL_ROUTE) {

        private const val MOVIE_DETAIL_ROUTE_WITH_ARGS = "movie_detail"

        fun createRoute(movieId: Int): String {
            return "$MOVIE_DETAIL_ROUTE_WITH_ARGS/$movieId"
        }
    }

    object FavoriteMovies : Screen(FAVORITE_MOVIES_ROUTE)
    object FavoriteMovieDetail : Screen(FAVORITE_MOVIE_DETAIL) {

        private const val FAVORITE_MOVIE_DETAIL = "favorite_movie_detail"

        fun createRoute(movieId: Int): String {
            return "$FAVORITE_MOVIE_DETAIL/$movieId"
        }
    }

    companion object {
        const val KEY_MOVIE_DETAIL = "movieId"

        const val HOME_ROUTE = "home"
        const val MOVIES_ROUTE = "movies"
        const val MOVIE_DETAIL_ROUTE = "movie_detail/{$KEY_MOVIE_DETAIL}"

        const val FAVORITE_MOVIES_ROUTE = "favorite_movies"
        const val FAVORITE_MOVIE_DETAIL = "favorite_movie_detail/{$KEY_MOVIE_DETAIL}"
    }
}