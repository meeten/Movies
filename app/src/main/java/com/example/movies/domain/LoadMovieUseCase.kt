package com.example.movies.domain

import javax.inject.Inject

class LoadMovieUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke(id: Int) = repository.loadMovie(id)
}