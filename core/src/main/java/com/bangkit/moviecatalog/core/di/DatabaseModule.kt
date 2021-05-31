package com.bangkit.moviecatalog.core.di

import android.content.Context
import androidx.room.Room
import com.bangkit.moviecatalog.core.data.source.local.room.DataDao
import com.bangkit.moviecatalog.core.data.source.local.room.MovieTvDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieTvDatabase = Room.databaseBuilder(
        context.applicationContext,
        MovieTvDatabase::class.java,
        "MovieTv.db"
    ).build()

    @Provides
    fun provideDataDao(database: MovieTvDatabase): DataDao = database.dataDao()
}