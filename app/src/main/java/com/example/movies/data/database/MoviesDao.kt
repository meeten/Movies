package com.example.movies.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {

    @Query("SELECT * FROM tableMovie")
    suspend fun loadMoviesFavorite(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieEntity)

    @Query("DELETE FROM tableMovie WHERE id = :id")
    suspend fun deleteMovie(id: Int)
}