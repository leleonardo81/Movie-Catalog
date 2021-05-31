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
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideEncryption() : SupportFactory {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("MovieCatalogLeo".toCharArray())
        return SupportFactory(passphrase)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieTvDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("MovieCatalogLeo".toCharArray())
        val supportFactory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context.applicationContext, MovieTvDatabase::class.java,
            "MovieTv.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(supportFactory)
            .build()
    }

    @Provides
    fun provideDataDao(database: MovieTvDatabase): DataDao = database.dataDao()
}