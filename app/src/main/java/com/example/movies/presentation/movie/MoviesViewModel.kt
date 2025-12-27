package com.example.movies.presentation.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.repository.MoviesRepository
import com.example.movies.domain.state.MoviesState
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {
    private val repository = MoviesRepository
    private val _uiState = MutableLiveData<MoviesState>(MoviesState.Initial)
    val uiState: LiveData<MoviesState> get() = _uiState

    init {
        _uiState.value = MoviesState.Loading
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            val movies = repository.loadMovies()

            _uiState.value = MoviesState.Movies(movies = movies)
        }
    }
}