package com.example.movies.data.repository

import com.example.movies.BuildConfig
import com.example.movies.data.mapper.MovieMapper
import com.example.movies.data.mapper.MoviesPreviewMapper
import com.example.movies.data.network.ApiFactory
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MoviePreview

object MoviesRepository {

    private val apiService = ApiFactory.apiService
    private val moviesPreviewMapper = MoviesPreviewMapper()
    private val movieMapper = MovieMapper()

    private val _movies = mutableListOf<MoviePreview>()
    val movies get() = _movies.toList()

    private var nextFrom: String? = null
    suspend fun loadMovies(): List<MoviePreview> {
        val startFrom = nextFrom

        if (startFrom == null && movies.isNotEmpty()) {
            return movies
        }

        val moviesResponse = if (startFrom == null) {
            apiService.loadMovies(getApiKey())
        } else {
            apiService.loadNextMovies(startFrom, getApiKey())
        }

        nextFrom = moviesResponse.moviesDto.next.takeIf {
            moviesResponse.moviesDto.hasNext
        }

        _movies.addAll(moviesPreviewMapper.mapResponseToMovies(moviesResponse))

        return movies
    }

    suspend fun loadMovie(id: Int): Movie {
        val movieResponse = apiService.loadMovie(
            id,
            getApiKey()
        )

        val movie = movieMapper.mapperResponseToMovie(movieResponse)
        return movie
    }

    private fun getApiKey(): String {
        return BuildConfig.API_KEY
    }
}