package com.example.movies.presentation.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movies.di.LocalViewModelFactory
import com.example.movies.presentation.home.MoviesContent
import com.example.movies.presentation.topBars.FavoriteMoviesTopBar
import com.example.movies.ui.theme.blue

@Composable
fun FavoriteMoviesScreen(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    val viewModelFactory = LocalViewModelFactory.current
    val viewModel: FavoritesViewModel =
        viewModel(factory = viewModelFactory)
    val favoriteMoviesState =
        viewModel.uiState.collectAsState(FavoritesScreenState.Initial).value

    FavoriteMoviesTopBar(onBackClick) {
        when (favoriteMoviesState) {
            FavoritesScreenState.Initial -> {
            }

            FavoritesScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(color = blue)
                }
            }

            is FavoritesScreenState.FavoriteMovies -> {
                MoviesContent(
                    movies = favoriteMoviesState.movies,
                    onMovieClick = onMovieClick,
                )
            }
        }
    }
}