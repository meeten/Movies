package com.example.movies.domain.usecases

import com.example.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class LoadedFavoritesUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke() = repository.loadedFavorites
}