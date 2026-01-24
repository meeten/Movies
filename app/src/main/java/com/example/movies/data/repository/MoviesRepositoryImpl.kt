package com.example.movies.data.repository

import android.util.Log
import com.example.movies.data.database.dao.MoviesDao
import com.example.movies.data.mapper.MovieMapper
import com.example.movies.data.network.ApiService
import com.example.movies.domain.MoviesRepository
import com.example.movies.domain.model.MovieDetail
import com.example.movies.domain.model.MoviePreview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val mapper: MovieMapper,
    private val moviesDao: MoviesDao,
    private val apiService: ApiService
) : MoviesRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val nextMoviesNeededEvents =
        MutableSharedFlow<Unit>(replay = 1)

    private var nextFrom: String? = null
    private val loadedMovies = flow {
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

            _movies.addAll(mapper.mapResponseToMovies(moviesResponse))
            emit(movies)
        }
    }.retry(2) {
        RETRY_TIMEOUT_MILLS
        true
    }.catch {
        Log.d("MoviesRepository", it.message.toString())
    }

    private val _movies = mutableListOf<MoviePreview>()
    private val movies: List<MoviePreview> get() = _movies.toList()

    override val data = loadedMovies.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = movies
    )

    override val loadedFavorites = flow {
        emit(moviesDao.loadMoviesFavorite())
    }

    override suspend fun loadNextMovies() {
        nextMoviesNeededEvents.emit(Unit)
    }

    override fun loadMovie(id: Int) = flow {
        var isLoadedFavorites = false
        loadedFavorites.collect {
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

        val movie = mapper.mapperResponseToMovie(movieResponse)
        emit(movie)
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )

    override suspend fun toggleFavorite(favoriteMovie: MovieDetail) {
        if (favoriteMovie.isFavorite) {
            moviesDao.saveMovie(favoriteMovie)
        } else {
            moviesDao.deleteMovie(favoriteMovie.id)
        }
    }

    companion object {
        private const val RETRY_TIMEOUT_MILLS = 3000L
    }
}