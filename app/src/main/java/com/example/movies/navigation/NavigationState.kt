package com.example.movies.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(val navController: NavHostController) {

    fun navigateToFavoriteMoviesScreen() {
        navController.navigate(Screen.FavoriteMovies.route)
    }

    fun navigateToMovieDetailScreen(movieId: Int) {
        navController.navigate(Screen.MovieDetail.createRoute(movieId))
    }
}

@Composable
fun rememberNavigationState(
    navController: NavHostController = rememberNavController()
): NavigationState {
    return remember { NavigationState(navController) }
}