package com.example.movies.domain.usecases

import com.example.movies.domain.repository.MoviesRepository
import com.example.movies.domain.entity.MovieDetail
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(private val repository: MoviesRepository) {

    suspend operator fun invoke(modifiedMovie: MovieDetail) =
        repository.toggleFavorite(modifiedMovie)
}