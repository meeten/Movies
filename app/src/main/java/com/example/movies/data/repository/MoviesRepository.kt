package com.example.movies.data.repository

import android.app.Application
import com.example.movies.BuildConfig
import com.example.movies.data.database.MoviesDatabase
import com.example.movies.data.mapper.MovieMapper
import com.example.movies.data.mapper.MoviesPreviewMapper
import com.example.movies.data.network.ApiFactory
import com.example.movies.domain.model.MovieDetail
import com.example.movies.domain.model.MoviePreview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class MoviesRepository private constructor(val application: Application) {

    private val database = MoviesDatabase.getInstance(application)
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val nextMoviesNeededEvents =
        MutableSharedFlow<Unit>(replay = 1)
    private val apiService = ApiFactory.apiService
    private val moviesPreviewMapper = MoviesPreviewMapper()
    private val movieMapper = MovieMapper()

    private val _movies = mutableListOf<MoviePreview>()
    private val movies: List<MoviePreview> get() = _movies.toList()

    private var nextFrom: String? = null
    val loadedMovies = flow {
        nextMoviesNeededEvents.emit(Unit)
        nextMoviesNeededEvents.collect {
            val startFrom = nextFrom

            if (startFrom == null && _movies.isNotEmpty()) {
                emit(movies)
                return@collect
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
            emit(movies)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = movies
    )

    val loadedFavoriteMovies = flow {
        emit(database.moviesDao.loadMoviesFavorite())
    }

    suspend fun loadNextMovies() {
        nextMoviesNeededEvents.emit(Unit)
    }

    fun loadMovie(id: Int) = flow {
        var isLoadedFavorites = false
        loadedFavoriteMovies.collect {
            it.firstOrNull { it.id == id }?.let { movieDetail ->
                emit(movieDetail)
                isLoadedFavorites = true
            }
        }

        if (isLoadedFavorites) return@flow

        val movieResponse = apiService.loadMovie(
            id,
            getApiKey()
        )

        val movie = movieMapper.mapperResponseToMovie(movieResponse)
        emit(movie)
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )

    suspend fun toggleFavorite(favoriteMovie: MovieDetail) {
        if (favoriteMovie.isFavorite) {
            database.moviesDao.saveMovie(favoriteMovie)
        } else {
            database.moviesDao.deleteMovie(favoriteMovie.id)
        }
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