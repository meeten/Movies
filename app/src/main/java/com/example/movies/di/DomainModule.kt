package com.example.movies.di

import com.example.movies.data.repository.MoviesRepositoryImpl
import com.example.movies.domain.MoviesRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @ApplicationScope
    @Binds
    fun bindMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository
}