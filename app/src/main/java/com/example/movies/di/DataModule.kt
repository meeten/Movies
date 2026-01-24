package com.example.movies.di

import android.app.Application
import com.example.movies.data.database.MoviesDatabase
import com.example.movies.data.database.dao.MoviesDao
import com.example.movies.data.network.ApiFactory
import com.example.movies.data.network.ApiService
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideApiService(): ApiService {
        return ApiFactory.apiService
    }

    companion object {
        @Provides
        fun provideMoviesDto(application: Application): MoviesDao {
            return MoviesDatabase.getInstance(application).moviesDao
        }
    }
}