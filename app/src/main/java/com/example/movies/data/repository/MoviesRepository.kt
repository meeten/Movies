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

    suspend fun loadMovies(): List<Movie> {
        val data = mapper.mapResponseToMovies(
            apiService.loadMovies(getApiKey())
        )

        _movies.addAll(data)

        return movies
    }

    private fun getApiKey(): String {
        return BuildConfig.API_KEY
    }
}