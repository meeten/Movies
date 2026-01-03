package com.example.movies.data.mapper

import com.example.movies.data.model.MoviesResponseDto
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Poster
import java.util.Locale

class MoviesMapper {

    fun mapResponseToMovies(responseDto: MoviesResponseDto): List<Movie> {
        val result = mutableListOf<Movie>()

        val docs = responseDto.moviesDto.docsDto

        for (doc in docs) {
            val movie = doc.movie
            val posterDto = movie.poster ?: continue

            val data = Movie(
                id = movie.id,
                name = movie.name ?: movie.alternativeName ?: continue,
                rating = String.format(Locale.US, "%.1f", movie.rating.kp),
                year = movie.year.toString(),
                poster = Poster(posterDto.url, posterDto.previewUrl)
            )

            result.add(data)
        }

        return result
    }
}