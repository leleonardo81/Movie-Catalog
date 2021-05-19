package com.bangkit.moviecatalog.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.bangkit.moviecatalog.data.source.local.entity.MovieModel

@Dao
interface DataDao {
    @Query("SELECT * FROM movietv WHERE type = :type")
    fun getDatas(type: String): PagingSource<Int, MovieModel>

    @Query("SELECT * FROM movietv WHERE type = :type AND id = :id")
    fun getDetail(type: String, id: Int): LiveData<MovieModel>

//    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDatas(movietvDatas: List<MovieModel>)

    @Update
    fun updateData(movietvData: MovieModel)

    @Query("SELECT * FROM movietv WHERE type = :type AND favorite = 1")
    fun getFavorite(type: String): PagingSource<Int, MovieModel>
}