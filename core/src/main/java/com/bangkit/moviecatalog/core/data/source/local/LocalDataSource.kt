package com.bangkit.moviecatalog.core.data.source.local

import android.util.Log
import androidx.paging.PagingSource
import com.bangkit.moviecatalog.core.data.source.local.entity.MovieEntity
import com.bangkit.moviecatalog.core.data.source.local.room.DataDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val mDataDao: DataDao) {

    fun getList(type: String): PagingSource<Int, MovieEntity> = mDataDao.getDatas(type)

    fun getDetail(type: String, id: Int): Flow<MovieEntity> = mDataDao.getDetail(type, id)

    suspend fun insertDatas(listData: List<MovieEntity>) = mDataDao.insertDatas(listData)

    fun getFavoriteList(type: String): PagingSource<Int, MovieEntity> = mDataDao.getFavorite(type)

    fun setFavorite(movieEntity: MovieEntity, state: Boolean) {
        Log.d("LOCALDATA", movieEntity.toString())
        movieEntity.isFavorite = state
        mDataDao.updateData(movieEntity)
    }
}