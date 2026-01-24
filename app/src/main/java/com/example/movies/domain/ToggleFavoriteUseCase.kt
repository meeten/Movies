package com.example.movies.domain

import com.example.movies.domain.model.MovieDetail
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(private val repository: MoviesRepository) {

    suspend operator fun invoke(modifiedMovie: MovieDetail) =
        repository.toggleFavorite(modifiedMovie)
}