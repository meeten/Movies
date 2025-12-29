package com.example.movies.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movies.domain.model.Movie

class NavigationState(val navController: NavHostController) {

    fun navigateToFavoriteMoviesScreen() {
        navController.navigate(Screen.FavoriteMovies.route)
    }

    fun navigateToFilmScreen(movie: Movie) {
        navController.navigate(Screen.Film.createRoute(movie))
    }
}

@Composable
fun rememberNavigationState(
    navController: NavHostController = rememberNavController()
): NavigationState {
    return remember { NavigationState(navController) }
}