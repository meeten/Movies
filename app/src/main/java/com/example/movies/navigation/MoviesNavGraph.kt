package com.example.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

fun NavGraphBuilder.moviesNavGraph(
    homeScreenContent: @Composable () -> Unit,
    movieDetailScreenContent: @Composable (Int) -> Unit,
) {
    navigation(
        startDestination = Screen.Movies.route,
        route = Screen.Home.route
    ) {
        composable(route = Screen.Movies.route) {
            homeScreenContent()
        }

        composable(
            route = Screen.MovieDetail.route,
            arguments = listOf(navArgument(Screen.KEY_MOVIE_DETAIL) {
                type = NavType.IntType
            })
        ) {
            val id = it.arguments?.getInt(Screen.KEY_MOVIE_DETAIL) ?: 0

            movieDetailScreenContent(id)
        }
    }
}