package com.example.movies.presentation.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.repository.MoviesRepository
import com.example.movies.domain.state.MovieState
import kotlinx.coroutines.launch

class MovieViewModel(private val id: Int) : ViewModel() {

    val repository = MoviesRepository

    private val _uiState = MutableLiveData<MovieState>(MovieState.Initial)
    val uiState: LiveData<MovieState> get() = _uiState

    init {
        _uiState.value = MovieState.Loading
        loadMovie()
    }

    fun loadMovie() {
        viewModelScope.launch {
            val movie = repository.loadMovie(id)
            _uiState.value = MovieState.Movie(movie)
        }
    }

    fun toggleFavorite(isFavorite: Boolean) {
        val currentState = _uiState.value
        if (currentState is MovieState.Movie) {
            val modifiedMovie = currentState.movie.copy(isFavorite = !isFavorite)
            repository.toggleFavorite(modifiedMovie)
            _uiState.value = MovieState.Movie(modifiedMovie)
        }
    }
}