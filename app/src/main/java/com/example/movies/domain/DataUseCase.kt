package com.example.movies.domain

import javax.inject.Inject

class DataUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke() = repository.data
}