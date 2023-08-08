package com.homework.mymoviebox.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.homework.mymoviebox.data.source.local.dao.MyMovieBoxDao
import com.homework.mymoviebox.data.source.local.entity.MovieEntity

@Database(
    entities = [
        MovieEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class MyMovieDatabase : RoomDatabase() {
    abstract fun myMovieBoxDao(): MyMovieBoxDao
}