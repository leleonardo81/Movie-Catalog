package com.bangkit.moviecatalog.di

import com.bangkit.moviecatalog.core.domain.usecase.MovieTvInteractor
import com.bangkit.moviecatalog.core.domain.usecase.MovieTvUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideMovieTvUseCase(movieTvInteractor: MovieTvInteractor) : MovieTvUseCase
}