package com.example.movies.presentation.movie

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
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
            MovieContent(
                viewModel,
                movieState.movie
            )
        }
    }
}

@Composable
fun MovieContent(
    viewModel: MovieViewModel,
    movie: Movie,
) {
    val context = LocalContext.current
    var isSuccessLoading by remember { mutableStateOf(false) }

    LazyColumn {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = movie.poster,
                    contentDescription = null,
                    onState = {
                        when (it) {
                            AsyncImagePainter.State.Empty -> {}
                            is AsyncImagePainter.State.Error -> {}
                            is AsyncImagePainter.State.Loading -> {}
                            is AsyncImagePainter.State.Success -> {
                                isSuccessLoading = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = {
                        viewModel.toggleFavorite(movie.isFavorite)
                    },
                    modifier = Modifier
                        .align(alignment = Alignment.BottomEnd)
                        .offset(x = (-20).dp, y = 22.dp)
                ) {
                    Image(
                        painter =
                            if (movie.isFavorite) painterResource(R.drawable.star_yellow)
                            else painterResource((R.drawable.star_gray)),
                        contentDescription = null,
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

        if (isSuccessLoading) {
            items(items = movie.trailers, key = { it.url }) { trailer ->
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, trailer.url.toUri())
                        context.startActivity(intent)
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(8.dp),
                    contentPadding = PaddingValues(vertical = 20.dp, horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = blue),

                    )
                {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = trailer.name,
                            modifier = Modifier.weight(1f),
                            color = Color.White
                        )

                        Image(
                            painter = painterResource(R.drawable.play_arrow),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}