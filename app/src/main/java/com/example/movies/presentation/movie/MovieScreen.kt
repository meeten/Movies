package com.example.movies.presentation.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.movies.R
import com.example.movies.domain.model.Movie
import com.example.movies.domain.state.MovieState
import com.example.movies.domain.state.MoviesState
import com.example.movies.ui.theme.blue

@Composable
fun MovieScreen(id: Int) {
    val viewModel: MovieViewModel = viewModel(factory = MovieViewModelFactory(id))
    val movieState = viewModel.uiState.observeAsState(MoviesState.Initial).value

    when (movieState) {
        MovieState.Initial -> {}

        MovieState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = blue)
            }
        }

        is MovieState.Movie -> {
            MovieContent(movieState.movie)
        }
    }
}

@Composable
fun MovieContent(movie: Movie) {
    val verticalScroll = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(state = verticalScroll)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = movie.poster,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = {
                },
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .offset(x = (-24).dp, y = 20.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.star_64px),
                    contentDescription = null,
                    modifier = Modifier.background(color = Color.DarkGray)
                )
            }
        }

        Text(
            text = movie.name,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Text(
            text = "${movie.year}",
            modifier = Modifier.padding(8.dp),
            color = Color.Yellow.copy(green = 0.5f),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Text(
            text = movie.description,
            modifier = Modifier.padding(8.dp)
        )
    }
}