package com.example.movies.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.example.movies.di.LocalAppComponent
import com.example.movies.di.LocalViewModelFactory
import com.example.movies.presentation.app.MovieApp
import com.example.movies.ui.theme.MoviesTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as MovieApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CompositionLocalProvider(
                LocalAppComponent provides component,
                LocalViewModelFactory provides viewModelFactory
            ) {
                MoviesTheme(isEnableEdgeToEdge = true) {
                    MainScreen()
                }
            }
        }
    }
}