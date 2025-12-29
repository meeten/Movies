package com.example.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movies.domain.model.Movie

@Composable
fun AppNavGraph(
    navController: NavHostController,
    moviesScreenContent: @Composable () -> Unit,
    filmScreenContent: @Composable (Movie) -> Unit,
    favoriteMoviesScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        moviesNavGraph(moviesScreenContent, filmScreenContent)

        composable(route = Screen.FavoriteMovies.route) {
            favoriteMoviesScreenContent()
        }
    }
}