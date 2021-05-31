package com.bangkit.moviecatalog.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bangkit.moviecatalog.core.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 2, exportSchema = false)
abstract class MovieTvDatabase: RoomDatabase() {
    abstract fun dataDao(): DataDao

}