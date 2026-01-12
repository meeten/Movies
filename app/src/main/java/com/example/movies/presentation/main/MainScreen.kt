package com.example.movies.presentation.main

import android.app.Application
import androidx.compose.runtime.Composable
import com.example.movies.navigation.AppNavGraph
import com.example.movies.navigation.rememberNavigationState
import com.example.movies.presentation.favorite.FavoriteMoviesScreen
import com.example.movies.presentation.home.HomeScreen
import com.example.movies.presentation.movie.MovieDetailScreen

@Composable
fun MainScreen(application: Application) {
    val navigationState = rememberNavigationState()

    AppNavGraph(
        navController = navigationState.navController,
        homeScreenContent = {
            HomeScreen(
                application = application,
                onMoreVertClick = {
                    navigationState.navigateToFavoriteMoviesScreen()
                },
                onMovieClick = { movieId ->
                    navigationState.navigateToMovieDetailScreen(movieId)
                })
        },
        movieScreenContent = {
            MovieDetailScreen(application, it) {
                navigationState.navController.popBackStack()
            }
        },
        favoriteMoviesScreenContent = {
            FavoriteMoviesScreen(
                application,
                onBackClick = {
                    navigationState.navController.popBackStack()
                },
                onMovieClick = { movieId ->
                    navigationState.navigateToMovieDetailScreen(movieId)
                }
            )
        },
        favoriteMovieDetailScreenContent = {
            MovieDetailScreen(application, it) {
                navigationState.navController.popBackStack()
            }
        }
    )
}