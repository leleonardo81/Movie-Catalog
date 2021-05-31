package com.bangkit.moviecatalog.core.data

import android.util.Log
import androidx.paging.*
import com.bangkit.moviecatalog.core.data.source.local.LocalDataSource
import com.bangkit.moviecatalog.core.data.source.local.entity.MovieEntity
import com.bangkit.moviecatalog.core.data.source.local.room.MovieTvDatabase
import com.bangkit.moviecatalog.core.data.source.remote.RemoteDataSource
import com.bangkit.moviecatalog.core.data.source.remote.api.ApiResponse
import com.bangkit.moviecatalog.core.data.source.remote.response.ResultsItem
import com.bangkit.moviecatalog.core.domain.model.MovieModel
import com.bangkit.moviecatalog.core.domain.repository.IDataRepository
import com.bangkit.moviecatalog.core.utils.AppExecutors
import com.bangkit.moviecatalog.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
    private val database: MovieTvDatabase
): IDataRepository {
    companion object {
        const val PAGE_SIZE = 4
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAll(type: String): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = object : NetworkMediator<MovieEntity, List<ResultsItem>>(database) {
                override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> = remoteDataSource.getList(type)

                override suspend fun saveCallResult(data: List<ResultsItem>) {
//                    val listData = data.results
//                    listData?.let {
                        val listItem = DataMapper.mapResponseToEntities(data, type)
                        localDataSource.insertDatas(listItem)
//                    }
                }

                override fun getKey(something: MovieEntity): Int? = something.id

            }
        ) { localDataSource.getList(type) }.flow.map { pagingData ->
            pagingData.map { DataMapper.mapEntityToDomain(it) }
        }
    }

    override fun getDetail(type: String, id: Int): Flow<MovieModel> =
        localDataSource.getDetail(type, id).map { DataMapper.mapEntityToDomain(it) }

    @OptIn(ExperimentalPagingApi::class)
    override fun getFavorites(type: String): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            remoteMediator = object : NetworkMediator<MovieEntity, List<ResultsItem>>(database) {
                override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> {
                    /** This is Dummy Call, somehow the PagingSource
                     *  won't update without this dummy mediator
                     */
                    return flow {
                        emit(ApiResponse.Success(ArrayList<ResultsItem>()) as ApiResponse<List<ResultsItem>>)
                    }
                }

                override suspend fun saveCallResult(data: List<ResultsItem>) { }

                override fun getKey(something: MovieEntity): Int? = something.id
            },
            pagingSourceFactory = { localDataSource.getFavoriteList(type) }
        ).flow.map { pagingData ->
            pagingData.map { DataMapper.mapEntityToDomain(it) }
        }
    }

    override fun setFavorite(movieModel: MovieModel, state: Boolean) {
        val entity = DataMapper.mapDomainToEntity(movieModel)
        Log.d("3", entity.toString())
        appExecutors.diskIO().execute { localDataSource.setFavorite(entity, state) }
    }

}