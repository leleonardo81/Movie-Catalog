package com.bangkit.moviecatalog.di

import android.content.Context
import com.bangkit.moviecatalog.data.DataRepository
import com.bangkit.moviecatalog.data.source.local.LocalDataSource
import com.bangkit.moviecatalog.data.source.local.room.MovieTvDatabase
import com.bangkit.moviecatalog.data.source.remote.RemoteDataSource
import com.bangkit.moviecatalog.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): DataRepository {

        val database = MovieTvDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.dataDao())
        val appExecutors = AppExecutors()

        return DataRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}