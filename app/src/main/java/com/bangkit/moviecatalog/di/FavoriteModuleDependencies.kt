package com.bangkit.moviecatalog.di

import com.bangkit.moviecatalog.core.domain.usecase.MovieTvUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun movieTvUseCase() : MovieTvUseCase

}