package com.example.movies.presentation.movie

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.repository.MoviesRepository
import com.example.movies.domain.state.MovieState
import com.example.movies.extensions.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    application: Application,
    id: Int,
) : ViewModel() {

    private val repository = MoviesRepository.getInstance(application)
    private val movieFlow = repository.loadMovie(id)
    private val refreshMovieFlow = MutableSharedFlow<MovieState>()

    val uiState = movieFlow
        .map { it?.let { movie -> MovieState.Movie(movie) as MovieState } ?: MovieState.Loading }
        .onStart { emit(MovieState.Loading) }
        .mergeWith(refreshMovieFlow)

    fun toggleFavorite(isFavorite: Boolean) {
        viewModelScope.launch {
            movieFlow.value?.let { movieDetail ->
                val modifiedMovie = movieDetail.copy(isFavorite = !isFavorite)
                repository.toggleFavorite(modifiedMovie)
                refreshMovieFlow.emit(MovieState.Movie(movieDetail))
            }
        }
    }
}