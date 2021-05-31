package com.bangkit.moviecatalog.core.di

import com.bangkit.moviecatalog.core.data.DataRepository
import com.bangkit.moviecatalog.core.domain.repository.IDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [ApiModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(dataRepository: DataRepository) : IDataRepository

}