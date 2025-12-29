package com.example.movies.presentation.movie

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.movies.domain.model.Movie

@Composable
fun FilmScreen(movie: Movie) {
    Text(text = movie.name)
}