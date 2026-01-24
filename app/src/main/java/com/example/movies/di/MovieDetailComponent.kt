package com.example.movies.di

import com.example.movies.presentation.movie.MovieDetailViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
interface MovieDetailComponent {

    fun movieDetailViewModelFactory(): MovieDetailViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance id: Int
        ): MovieDetailComponent
    }
}