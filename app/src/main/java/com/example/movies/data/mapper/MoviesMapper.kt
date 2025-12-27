package com.example.movies.data.mapper

import com.example.movies.data.model.MoviesResponseDto
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Poster

class MoviesMapper {

    fun mapResponseToMovies(responseDto: MoviesResponseDto): List<Movie> {
        val result = mutableListOf<Movie>()

        val movies = responseDto.movies

        for (response in movies) {
            val posterDto = response.poster ?: continue

            val data = Movie(
                id = response.id,
                name = response.name ?: response.alternativeName ?: continue,
                rating = response.rating.imdb.toString(),
                year = response.year.toString(),
                description = response.description ?: "",
                poster = Poster(posterDto.previewUrl, posterDto.url)
            )

            result.add(data)
        }

        return result
    }
}