package com.example.movies.domain.usecases

import com.example.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class LoadMovieUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke(id: Int) = repository.loadMovie(id)
}