package com.example.movies.data.network

import com.example.movies.data.model.MoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("v1.4/movie?limit=10")
    suspend fun loadMovies(
        @Query("page") page: String,
        @Header("X-API-KEY") apiKey: String
    ): MoviesResponseDto
}