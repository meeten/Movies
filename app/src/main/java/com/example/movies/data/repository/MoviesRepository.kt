package com.example.movies.data.repository

import android.app.Application
import com.example.movies.BuildConfig
import com.example.movies.data.database.MoviesDatabase
import com.example.movies.data.mapper.MovieMapper
import com.example.movies.data.mapper.MoviesPreviewMapper
import com.example.movies.data.network.ApiFactory
import com.example.movies.domain.model.MovieDetail
import com.example.movies.domain.model.MoviePreview

class MoviesRepository private constructor(val application: Application) {

    private val database = MoviesDatabase.getInstance(application)
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

    suspend fun loadMovie(id: Int): MovieDetail {
        loadFavoriteMovies().firstOrNull { it.id == id }?.let {
            return it
        }

        val movieResponse = apiService.loadMovie(
            id,
            getApiKey()
        )

        val movie = movieMapper.mapperResponseToMovie(movieResponse)
        return movie
    }


    suspend fun toggleFavorite(favoriteMovie: MovieDetail) {
        if (favoriteMovie.isFavorite) {
            database.moviesDao.saveMovie(favoriteMovie)
        } else {
            database.moviesDao.deleteMovie(favoriteMovie.id)
        }
    }

    suspend fun loadFavoriteMovies(): List<MovieDetail> {
        return database.moviesDao.loadMoviesFavorite()
    }

    private fun getApiKey(): String {
        return BuildConfig.API_KEY
    }

    companion object {

        @Volatile
        private var instance: MoviesRepository? = null

        fun getInstance(application: Application): MoviesRepository {
            instance?.let { return it }

            synchronized(this) {
                instance?.let { return it }

                return MoviesRepository(application).also { instance = it }
            }
        }
    }
}