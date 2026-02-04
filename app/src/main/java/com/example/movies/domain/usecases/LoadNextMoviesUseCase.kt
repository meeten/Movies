package com.example.movies.domain.usecases

import com.example.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class LoadNextMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {

    suspend operator fun invoke() = repository.loadNextMovies()
}