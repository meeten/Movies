package com.example.movies.presentation.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.repository.MoviesRepository
import com.example.movies.domain.state.MoviesState
import com.example.movies.extensions.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : ViewModel() {

    private val repository = MoviesRepository.getInstance(application)
    private val moviesStateFlow = repository.loadedMovies
    private val nextMoviesNeededEvents = MutableSharedFlow<Unit>()
    private val nextMoviesFlow = flow {
        nextMoviesNeededEvents.collect {
            emit(
                MoviesState.Movies(
                    movies = moviesStateFlow.value,
                    isLoadingNextMovies = true
                )
            )
        }
    }

    val uiState = moviesStateFlow
        .filter { it.isNotEmpty() }
        .map { MoviesState.Movies(it) as MoviesState }
        .onStart { emit(MoviesState.Loading) }
        .mergeWith(nextMoviesFlow)

    fun loadNextMovies() {
        viewModelScope.launch {
            nextMoviesNeededEvents.emit(Unit)
            repository.loadNextMovies()
        }
    }
}