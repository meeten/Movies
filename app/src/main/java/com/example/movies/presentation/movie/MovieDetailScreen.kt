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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.movies.R
import com.example.movies.di.LocalAppComponent
import com.example.movies.domain.model.MovieDetail
import com.example.movies.domain.state.MovieState
import com.example.movies.presentation.topBars.MovieTopBar
import com.example.movies.ui.theme.blue

@Composable
fun MovieDetailScreen(
    id: Int,
    onBackClick: () -> Unit
) {
    val appComponent = LocalAppComponent.current
    val movieDetailComponent = remember(id) {
        appComponent.movieDetailComponentFactory().create(id)
    }

    val viewModel: MovieDetailViewModel =
        viewModel(factory = movieDetailComponent.movieDetailViewModelFactory())
    val movieState = viewModel.uiState.collectAsState(MovieState.Initial).value

    MovieTopBar(onBackClick) {
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
                MovieDetailContent(
                    viewModel,
                    movieState.movie
                )
            }
        }
    }
}


@Composable
fun MovieDetailContent(
    viewModel: MovieDetailViewModel,
    movie: MovieDetail,
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
                                isSuccessLoading = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                if (isSuccessLoading) {
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
            }
        }

        if (isSuccessLoading) {
            item {
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