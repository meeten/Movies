package com.example.movies.di

import android.app.Application
import com.example.movies.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        ViewModelModule::class,
        MovieDetailSubcomponentsModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)

    fun movieDetailComponentFactory(): MovieDetailComponent.Factory

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}