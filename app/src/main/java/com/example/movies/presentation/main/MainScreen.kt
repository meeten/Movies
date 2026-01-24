package com.example.movies.presentation.main

import androidx.compose.runtime.Composable
import com.example.movies.navigation.AppNavGraph
import com.example.movies.navigation.rememberNavigationState
import com.example.movies.presentation.favorite.FavoriteMoviesScreen
import com.example.movies.presentation.home.HomeScreen
import com.example.movies.presentation.movie.MovieDetailScreen

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()

    AppNavGraph(
        navController = navigationState.navController,
        homeScreenContent = {
            HomeScreen(
                onMoreVertClick = {
                    navigationState.navigateToFavoriteMoviesScreen()
                },
                onMovieClick = { movieId ->
                    navigationState.navigateToMovieDetailScreen(movieId)
                })
        },
        movieScreenContent = {
            MovieDetailScreen(it) {
                navigationState.navController.popBackStack()
            }
        },
        favoriteMoviesScreenContent = {
            FavoriteMoviesScreen(
                onBackClick = {
                    navigationState.navController.popBackStack()
                },
                onMovieClick = { movieId ->
                    navigationState.navigateToMovieDetailScreen(movieId)
                }
            )
        },
        favoriteMovieDetailScreenContent = {
            MovieDetailScreen(it) {
                navigationState.navController.popBackStack()
            }
        }
    )
}