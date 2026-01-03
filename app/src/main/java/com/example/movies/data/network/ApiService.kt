package com.example.movies.data.network

import com.example.movies.data.model.MoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("v1.5/list/top250?limit=10")
    suspend fun loadMovies(
        @Header("X-API-KEY") apiKey: String,
    ): MoviesResponseDto
}