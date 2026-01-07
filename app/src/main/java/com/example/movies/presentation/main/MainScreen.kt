package com.example.movies.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.movies.R
import com.example.movies.navigation.AppNavGraph
import com.example.movies.navigation.rememberNavigationState
import com.example.movies.presentation.favorite.FavoriteMoviesScreen
import com.example.movies.presentation.movie.MovieScreen
import com.example.movies.presentation.home.HomeScreen
import com.example.movies.ui.theme.blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = stringResource(R.string.app_name)) },
            actions = {
                IconButton(onClick = {
                    navigationState.navigateToFavoriteMoviesScreen()
                }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = blue,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White
            )
        )
    }) {
        Column(modifier = Modifier.padding(it)) {
            AppNavGraph(
                navController = navigationState.navController,
                homeScreenContent = {
                    HomeScreen { movieId ->
                        navigationState.navigateToFilmScreen(movieId)
                    }
                },
                movieScreenContent = {
                    MovieScreen(it)
                },
                favoriteMoviesScreenContent = {
                    FavoriteMoviesScreen()
                },
            )
        }
    }
}