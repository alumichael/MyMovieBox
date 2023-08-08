package com.homework.mymoviebox.di

import android.app.Application
import androidx.room.Room
import com.homework.mymoviebox.BuildConfig
import com.homework.mymoviebox.data.Constants.DATABASE_NAME
import com.homework.mymoviebox.data.source.local.dao.MyMovieBoxDao
import com.homework.mymoviebox.data.source.local.MyMovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Singleton
    @Provides
    fun provideDatabase(application: Application): MyMovieDatabase =
        Room
            .databaseBuilder(application, MyMovieDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .openHelperFactory(SupportFactory(SQLiteDatabase.getBytes(BuildConfig.PARAPHRASE.toCharArray())))
            .build()

    @Provides
    fun provideMyMovieBoxDao(database: MyMovieDatabase): MyMovieBoxDao = database.myMovieBoxDao()
}