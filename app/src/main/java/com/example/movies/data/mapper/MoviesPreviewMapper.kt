package com.example.movies.data.mapper

import com.example.movies.data.model.MoviesResponseDto
import com.example.movies.domain.model.MoviePreview
import java.util.Locale

class MoviesPreviewMapper {

    fun mapResponseToMovies(responseDto: MoviesResponseDto): List<MoviePreview> {
        val result = mutableListOf<MoviePreview>()

        val docs = responseDto.moviesDto.docsDto

        for (doc in docs) {
            val movie = doc.movie
            val posterDto = movie.poster ?: continue

            val data = MoviePreview(
                id = movie.id,
                rating = String.format(Locale.US, "%.1f", movie.rating.kp),
                previewPoster = posterDto.previewUrl
            )

            result.add(data)
        }

        return result
    }
}