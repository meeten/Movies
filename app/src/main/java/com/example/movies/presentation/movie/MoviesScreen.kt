package com.example.movies.presentation.movie

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.movies.domain.model.Movie
import com.example.movies.domain.state.MoviesState
import com.example.movies.ui.theme.blue

@Composable
fun MoviesScreen() {
    val viewModel: MoviesViewModel = viewModel()
    val uiState = viewModel.uiState.observeAsState(MoviesState.Initial).value

    when (uiState) {
        is MoviesState.Initial -> {}

        is MoviesState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = blue
                )
            }
        }

        is MoviesState.Movies -> {
            MoviesContent(uiState.movies)
        }
    }
}

@Composable
fun MoviesContent(movies: List<Movie>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(items = movies, key = { it.id }) { movie ->
            MovieContent(movie)
        }
    }
}

@Composable
fun MovieContent(movie: Movie) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp)),
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        Box {
            AsyncImage(
                model = movie.poster.previewUrl,
                contentDescription = null,
                modifier = Modifier.aspectRatio(2f / 3f),
                contentScale = ContentScale.FillBounds
            )

            Box(
                modifier = Modifier
                    .padding(15.dp)
                    .clip(CircleShape)
                    .background(color = Color.Green.copy(alpha = 0.9f, green = 0.7f))
                    .align(Alignment.TopEnd)
            ) {
                Text(
                    text = movie.rating,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}