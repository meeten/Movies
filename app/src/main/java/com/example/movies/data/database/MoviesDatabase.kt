package com.example.movies.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movies.data.database.dao.MoviesDao
import com.example.movies.domain.entity.MovieDetail

@Database(entities = [MovieDetail::class], version = 1, exportSchema = false)
@TypeConverters(Convertors::class)
abstract class MoviesDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: MoviesDatabase? = null
        private const val BD_NAME = "database"

        fun getInstance(application: Application): MoviesDatabase {
            instance?.let {
                return it
            }
            synchronized(this) {
                instance?.let {
                    return it
                }

                val database = Room.databaseBuilder(
                    application.applicationContext,
                    MoviesDatabase::class.java,
                    BD_NAME,
                ).build()
                return database.also { instance = database }
            }
        }
    }

    abstract val moviesDao: MoviesDao
}