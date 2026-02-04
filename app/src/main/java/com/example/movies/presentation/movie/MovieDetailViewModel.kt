package com.example.movies.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.LoadMovieUseCase
import com.example.movies.domain.ToggleFavoriteUseCase
import com.example.movies.extensions.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    loadMovieUseCase: LoadMovieUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    movieId: Int
) : ViewModel() {

    private val movieFlow = loadMovieUseCase(movieId)
    private val refreshMovieFlow = MutableSharedFlow<MovieScreenState>()

    val uiState = movieFlow
        .map {
            it?.let { movie -> MovieScreenState.Movie(movie) as MovieScreenState }
                ?: MovieScreenState.Loading
        }
        .onStart { emit(MovieScreenState.Loading) }
        .mergeWith(refreshMovieFlow)

    fun toggleFavorite(isFavorite: Boolean) {
        viewModelScope.launch {
            movieFlow.value?.let { movie ->
                val modifiedMovie = movie.copy(isFavorite = !isFavorite)
                toggleFavoriteUseCase(modifiedMovie)
                refreshMovieFlow.emit(MovieScreenState.Movie(modifiedMovie))
            }
        }
    }
}