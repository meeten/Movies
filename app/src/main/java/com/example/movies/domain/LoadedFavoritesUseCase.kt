package com.example.movies.domain

import javax.inject.Inject

class LoadedFavoritesUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke() = repository.loadedFavorites
}