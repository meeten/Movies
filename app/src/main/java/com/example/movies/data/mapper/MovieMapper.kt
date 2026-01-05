package com.example.movies.data.mapper

import com.example.movies.data.model.MovieDto
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Trailer

class MovieMapper {
    fun mapperResponseToMovie(movieDto: MovieDto): Movie {
        val trailers = movieDto.videosDto?.trailers?.map {
            Trailer(
                url = it.url,
                name = it.name
            )
        } ?: emptyList()

        val movie = Movie(
            id = movieDto.id,
            name = movieDto.name ?: movieDto.alternativeName ?: "",
            year = movieDto.year,
            description = movieDto.description,
            poster = movieDto.posterDto.url,
            trailers = trailers
        )

        return movie
    }
}