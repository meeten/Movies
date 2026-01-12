package com.example.movies.presentation.favorite

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movies.domain.state.FavoriteMoviesState
import com.example.movies.presentation.home.MoviesContent
import com.example.movies.presentation.topBars.FavoriteMoviesTopBar
import com.example.movies.ui.theme.blue

@Composable
fun FavoriteMoviesScreen(
    application: Application,
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    val viewModel: FavoriteMoviesViewModel =
        viewModel(factory = FavoriteMoviesViewModelFactory(application))
    val favoriteMoviesState =
        viewModel.uiState.collectAsState(FavoriteMoviesState.Initial).value

    FavoriteMoviesTopBar(onBackClick) {
        when (favoriteMoviesState) {
            FavoriteMoviesState.Initial -> {
            }

            FavoriteMoviesState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(color = blue)
                }
            }

            is FavoriteMoviesState.FavoriteMovies -> {
                MoviesContent(
                    movies = favoriteMoviesState.movies,
                    onMovieClick = onMovieClick,
                )
            }
        }
    }
}