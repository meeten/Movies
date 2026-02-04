package com.example.movies.presentation.home

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.movies.di.LocalViewModelFactory
import com.example.movies.domain.entity.BaseMovie
import com.example.movies.presentation.topBars.HomeTopBar
import com.example.movies.ui.theme.blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMoreVertClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
) {
    val viewModelFactory = LocalViewModelFactory.current
    val viewModel: HomeViewModel = viewModel(factory = viewModelFactory)
    val moviesState = viewModel.uiState.collectAsState(MoviesScreenState.Initial).value


    HomeTopBar(onMoreVertClick = onMoreVertClick) {
        when (moviesState) {
            is MoviesScreenState.Initial -> {}

            is MoviesScreenState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = blue
                    )
                }
            }

            is MoviesScreenState.Error -> {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = moviesState.error.message.toString())
                }
            }

            is MoviesScreenState.Movies -> {
                MoviesContent(
                    viewModel = viewModel,
                    movies = moviesState.movies,
                    onMovieClick = onMovieClick,
                    isLoadingNextMovies = moviesState.isLoadingNextMovies
                )
            }
        }
    }
}

@Composable
fun MoviesContent(
    viewModel: HomeViewModel? = null,
    movies: List<BaseMovie>,
    onMovieClick: (Int) -> Unit,
    isLoadingNextMovies: Boolean? = null,
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
            if (isLoadingNextMovies ?: false) {
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
                    viewModel?.loadNextMovies()
                }
            }
        }
    }
}

@Composable
fun MovieContent(
    movie: BaseMovie,
    onMovieClick: (Int) -> Unit
) {
    val context = LocalContext.current
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
                model = ImageRequest.Builder(context)
                    .data(movie.posterUrl)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = null,
                imageLoader = context.imageLoader,
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
                modifier = Modifier.aspectRatio(2f / 3f),
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