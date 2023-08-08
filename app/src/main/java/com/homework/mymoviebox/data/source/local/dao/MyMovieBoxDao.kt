package com.homework.mymoviebox.data.source.local.dao

import androidx.room.*
import com.homework.mymoviebox.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyMovieBoxDao {
    @Query("SELECT * FROM movies")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getFavoriteMovie(id: Int): Flow<MovieEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(movie: MovieEntity): Long

    @Delete
    suspend fun removeFromFavorite(movie: MovieEntity): Int
}