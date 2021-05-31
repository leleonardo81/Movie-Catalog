package com.bangkit.moviecatalog.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import com.bangkit.moviecatalog.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DataDao {
    @Query("SELECT * FROM movietv WHERE type = :type")
    fun getDatas(type: String): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movietv WHERE type = :type AND id = :id")
    fun getDetail(type: String, id: Int): Flow<MovieEntity>

//    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDatas(movietvDatas: List<MovieEntity>)

    @Update
    fun updateData(movietvData: MovieEntity)

    @Query("SELECT * FROM movietv WHERE type = :type AND favorite = 1")
    fun getFavorite(type: String): PagingSource<Int, MovieEntity>
}