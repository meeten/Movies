package com.example.movies.domain

import javax.inject.Inject

class LoadNextMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {

    suspend operator fun invoke() = repository.loadNextMovies()
}