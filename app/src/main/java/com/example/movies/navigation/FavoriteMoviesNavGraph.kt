package com.example.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.favoriteMoviesNavGraph(
    favoriteMoviesScreenContent: @Composable () -> Unit,
    favoriteMovieDetailScreenContent: @Composable (Int) -> Unit
) {
    composable(route = Screen.FavoriteMovies.route) {
        favoriteMoviesScreenContent()
    }
    composable(
        route = Screen.FavoriteMovieDetail.route,
        arguments = listOf(navArgument(Screen.KEY_MOVIE_DETAIL) {
            type = NavType.IntType
        })
    ) {
        val id = it.arguments?.getInt(Screen.FAVORITE_MOVIE_DETAIL) ?: 0

        favoriteMovieDetailScreenContent(id)
    }
}