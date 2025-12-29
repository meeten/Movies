package com.example.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.movies.domain.model.Movie
import com.google.gson.Gson

fun NavGraphBuilder.moviesNavGraph(
    moviesScreenContent: @Composable () -> Unit,
    filmScreenContent: @Composable (Movie) -> Unit,
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
                type = NavType.StringType
            })
        ) {
            val movie = Gson().fromJson<Movie>(
                it.arguments?.getString(Screen.KEY_MOVIE),
                Movie::class.java
            )

            filmScreenContent(movie)
        }
    }
}