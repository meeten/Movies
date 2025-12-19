package com.example.movies.data.repository

import com.example.movies.data.mapper.MoviesMapper
import com.example.movies.data.network.ApiFactory
import com.example.movies.domain.model.Movie

object MoviesRepository {

    private val apiService = ApiFactory.apiService
    private val mapper = MoviesMapper()

    private val _movies = mutableListOf<Movie>()
    val movies get() = _movies.toList()

    suspend fun loadMovies(): List<Movie> {
        val moviesResponse = apiService.loadMovies()
        val data = mapper.mapResponseToMovies(moviesResponse)

        _movies.addAll(data)

        return movies
    }
}