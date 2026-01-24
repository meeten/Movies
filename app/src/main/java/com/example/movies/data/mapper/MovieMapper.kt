package com.example.movies.data.mapper

import com.example.movies.data.model.MovieDto
import com.example.movies.data.model.MoviesResponseDto
import com.example.movies.domain.model.MovieDetail
import com.example.movies.domain.model.MoviePreview
import com.example.movies.domain.model.Trailer
import java.util.Locale
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun mapperResponseToMovie(movieDto: MovieDto): MovieDetail {
        val trailers = movieDto.videosDto?.trailers?.map {
            Trailer(
                url = it.url,
                name = it.name
            )
        } ?: emptyList()

        val movie = MovieDetail(
            id = movieDto.id,
            name = movieDto.name ?: movieDto.alternativeName ?: "",
            year = movieDto.year,
            description = movieDto.description,
            posterUrl = movieDto.posterDto.url,
            rating = String.format(Locale.US, "%.1f", movieDto.ratingDto.kp),
            trailers = if (trailers.size >= 4) trailers.reversed().subList(0, 4) else trailers
        )

        return movie
    }

    fun mapResponseToMovies(responseDto: MoviesResponseDto): List<MoviePreview> {
        val result = mutableListOf<MoviePreview>()

        val docs = responseDto.moviesDto.docsDto

        for (doc in docs) {
            val movie = doc.moviePreviewDto
            val posterDto = movie.poster ?: continue

            val data = MoviePreview(
                id = movie.id,
                rating = String.format(Locale.US, "%.1f", movie.rating.kp),
                posterUrl = posterDto.previewUrl
            )

            result.add(data)
        }

        return result
    }
}