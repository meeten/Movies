package com.example.movies.presentation.movie

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import coil.compose.AsyncImagePainter
import com.example.movies.domain.model.MoviePreview
import com.example.movies.domain.state.MoviesState
import com.example.movies.ui.theme.blue

@Composable
fun MoviesScreen(onMovieClick: (Int) -> Unit) {
    val viewModel: MoviesViewModel = viewModel()
    val moviesState = viewModel.uiState.observeAsState(MoviesState.Initial).value

    when (moviesState) {
        is MoviesState.Initial -> {}

        is MoviesState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = blue
                )
            }
        }

        is MoviesState.Movies -> {
            MoviesContent(
                viewModel = viewModel,
                movies = moviesState.movies,
                onMovieClick = onMovieClick,
                isLoadingNextMovies = moviesState.isLoadingNextMovies
            )
        }
    }
}

@Composable
fun MoviesContent(
    viewModel: MoviesViewModel,
    movies: List<MoviePreview>,
    onMovieClick: (Int) -> Unit,
    isLoadingNextMovies: Boolean,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(items = movies, key = { it.id }) { movie ->
            MovieContent(movie, onMovieClick)
        }

        item(span = { GridItemSpan(2) }) {
            if (isLoadingNextMovies) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = blue,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                SideEffect {
                    viewModel.loadNextMovies()
                }
            }
        }
    }
}

@Composable
fun MovieContent(
    movie: MoviePreview,
    onMovieClick: (Int) -> Unit
) {
    var isLoaded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onMovieClick(movie.id)
            },
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        Box {
            AsyncImage(
                model = movie.previewPoster,
                contentDescription = null,
                modifier = Modifier.aspectRatio(2f / 3f),
                onState = {
                    when (it) {
                        AsyncImagePainter.State.Empty -> {}
                        is AsyncImagePainter.State.Error -> {}
                        is AsyncImagePainter.State.Loading -> {}
                        is AsyncImagePainter.State.Success -> {
                            isLoaded = true
                        }
                    }
                },
                contentScale = ContentScale.FillBounds
            )

            if (isLoaded) {
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
}