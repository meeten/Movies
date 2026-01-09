package com.example.movies.presentation.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.repository.MoviesRepository
import com.example.movies.domain.state.MoviesState
import kotlinx.coroutines.launch

class HomeViewModel(private val application: Application) : ViewModel() {
    private val repository = MoviesRepository.getInstance(application)
    private val _uiState = MutableLiveData<MoviesState>(MoviesState.Initial)
    val uiState: LiveData<MoviesState> get() = _uiState

    init {
        _uiState.value = MoviesState.Loading
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            try {
                val movies = repository.loadMovies()
                _uiState.value = MoviesState.Movies(movies = movies)
            } catch (e: Exception) {
                _uiState.value = MoviesState.Error(error = e)
            }
        }
    }

    fun loadNextMovies() {
        _uiState.value = MoviesState.Movies(
            movies = repository.movies,
            isLoadingNextMovies = true
        )

        loadMovies()
    }
}