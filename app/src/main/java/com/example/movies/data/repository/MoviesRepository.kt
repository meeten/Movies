package com.example.movies.data.repository

import com.example.movies.BuildConfig
import com.example.movies.data.mapper.MoviesMapper
import com.example.movies.data.network.ApiFactory
import com.example.movies.domain.model.Movie

object MoviesRepository {

    private val apiService = ApiFactory.apiService
    private val mapper = MoviesMapper()

    private val _movies = mutableListOf<Movie>()
    val movies get() = _movies.toList()

    private var nextFrom: String? = null
    suspend fun loadMovies(): List<Movie> {
        val startFrom = nextFrom

        if (startFrom == null && movies.isNotEmpty()) {
            return movies
        }

        val responseDto = if (startFrom == null) {
            apiService.loadMovies(getApiKey())
        } else {
            apiService.loadNextMovies(startFrom, getApiKey())
        }

        nextFrom = responseDto.moviesDto.next.takeIf {
            responseDto.moviesDto.hasNext
        }

        _movies.addAll(mapper.mapResponseToMovies(responseDto))

        return movies
    }

    private fun getApiKey(): String {
        return BuildConfig.API_KEY
    }
}