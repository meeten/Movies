package com.example.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navController: NavHostController,
    homeScreenContent: @Composable () -> Unit,
    movieScreenContent: @Composable (Int) -> Unit,
    favoriteMoviesScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        moviesNavGraph(homeScreenContent, movieScreenContent)

        composable(route = Screen.FavoriteMovies.route) {
            favoriteMoviesScreenContent()
        }
    }
}