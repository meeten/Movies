package com.example.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

fun NavGraphBuilder.moviesNavGraph(
    moviesScreenContent: @Composable () -> Unit,
    filmScreenContent: @Composable (Int) -> Unit,
) {
    navigation(
        startDestination = Screen.Movies.route,
        route = Screen.Home.route
    ) {
        composable(route = Screen.Movies.route) {
            moviesScreenContent()
        }

        composable(
            route = Screen.Film.route,
            arguments = listOf(navArgument(Screen.KEY_MOVIE) {
                type = NavType.IntType
            })
        ) {
            val id = it.arguments?.getInt(Screen.KEY_MOVIE) ?: 0

            filmScreenContent(id)
        }
    }
}