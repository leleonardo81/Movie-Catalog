package com.bangkit.moviecatalog.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit.moviecatalog.data.source.local.entity.MovieModel

@Database(entities = [MovieModel::class], version = 2, exportSchema = false)
abstract class MovieTvDatabase: RoomDatabase() {
    abstract fun dataDao(): DataDao

//    abstract suspend fun <R> withTransaction(block: suspend () -> R): R

    companion object {

        @Volatile
        private var INSTANCE: MovieTvDatabase? = null

        fun getInstance(context: Context): MovieTvDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieTvDatabase::class.java,
                    "MovieTv.db"
                ).build().apply {
                    INSTANCE = this
                }
            }

        fun getInstance(): MovieTvDatabase? = INSTANCE

    }
}