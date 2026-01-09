package com.example.movies.presentation.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.repository.MoviesRepository
import com.example.movies.domain.state.FavoriteMoviesState
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(application: Application) : ViewModel() {

    val repository = MoviesRepository.getInstance(application)
    private val _uiState = MutableLiveData<FavoriteMoviesState>(FavoriteMoviesState.Initial)
    val uiState: LiveData<FavoriteMoviesState> get() = _uiState

    init {
        _uiState.value = FavoriteMoviesState.Loading
        loadFavoriteMovies()
    }

    fun loadFavoriteMovies() {
        viewModelScope.launch {
            _uiState.value = FavoriteMoviesState
                .FavoriteMovies(repository.loadFavoriteMovies())
        }
    }
}