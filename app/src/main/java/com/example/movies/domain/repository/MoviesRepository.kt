package com.example.movies.domain.repository

import com.example.movies.BuildConfig
import com.example.movies.domain.entity.MovieDetail
import com.example.movies.domain.entity.MoviePreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MoviesRepository {

    val data: StateFlow<List<MoviePreview>>

    val loadedFavorites: Flow<List<MovieDetail>>

    suspend fun loadNextMovies()

    fun loadMovie(id: Int): StateFlow<MovieDetail?>

    suspend fun toggleFavorite(favoriteMovie: MovieDetail)

    fun getApiKey(): String {
        return BuildConfig.API_KEY
    }
}