package com.example.movies.data.repository

import android.util.Log
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
        val data = mutableListOf<Movie>()
        var page = 1
        while (data.size < 10) {
            val moviesResponse = apiService.loadMovies("$page", apiKey = BuildConfig.API_KEY)
            data.addAll(mapper.mapResponseToMovies(moviesResponse))
            page++
        }

        Log.d("MOVIES", data.toString())

        _movies.addAll(data)

        return movies
    }
}