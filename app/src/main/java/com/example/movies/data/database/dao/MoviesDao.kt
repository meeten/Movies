package com.example.movies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.domain.model.MovieDetail

@Dao
interface MoviesDao {

    @Query("SELECT * FROM tableMovie")
    suspend fun loadMoviesFavorite(): List<MovieDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieDetail)

    @Query("DELETE FROM tableMovie WHERE id = :id")
    suspend fun deleteMovie(id: Int)
}