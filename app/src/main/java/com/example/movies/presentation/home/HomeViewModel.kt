package com.example.movies.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.DataUseCase
import com.example.movies.domain.LoadNextMoviesUseCase
import com.example.movies.extensions.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    dataUseCase: DataUseCase,
    private val loadNextMoviesUseCase: LoadNextMoviesUseCase
) : ViewModel() {

    private val moviesStateFlow = dataUseCase()
    private val nextMoviesNeededEvents = MutableSharedFlow<Unit>()
    private val nextMoviesFlow = flow {
        nextMoviesNeededEvents.collect {
            emit(
                MoviesScreenState.Movies(
                    movies = moviesStateFlow.value,
                    isLoadingNextMovies = true
                )
            )
        }
    }

    val uiState = moviesStateFlow
        .filter { it.isNotEmpty() }
        .map { MoviesScreenState.Movies(it) as MoviesScreenState }
        .onStart { emit(MoviesScreenState.Loading) }
        .mergeWith(nextMoviesFlow)

    fun loadNextMovies() {
        viewModelScope.launch {
            nextMoviesNeededEvents.emit(Unit)
            loadNextMoviesUseCase()
        }
    }
}