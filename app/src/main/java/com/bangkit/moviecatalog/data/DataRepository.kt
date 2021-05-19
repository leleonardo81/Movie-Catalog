package com.bangkit.moviecatalog.data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bangkit.moviecatalog.BuildConfig
import com.bangkit.moviecatalog.data.source.local.LocalDataSource
import com.bangkit.moviecatalog.data.source.local.entity.MovieModel
import com.bangkit.moviecatalog.data.source.remote.RemoteDataSource
import com.bangkit.moviecatalog.data.source.remote.response.Response
import com.bangkit.moviecatalog.utils.AppExecutors
import kotlinx.coroutines.flow.Flow

class DataRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): DataSource {
    companion object {
        @Volatile
        private var instance: DataRepository? = null
        fun getInstance(
            remoteData: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(remoteData, localDataSource, appExecutors).apply { instance = this }
            }

        const val PAGE_SIZE = 4
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAll(type: String): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = object : NetworkMediator<MovieModel, Response>() {
                override suspend fun createCall(): Response = remoteDataSource.getList(type)

                override suspend fun saveCallResult(data: Response) {
                    val listItem = ArrayList<MovieModel>()
                    val listData = data.results
                    listData?.forEach { resultsItem -> resultsItem?.let {
                        listItem.add(
                            MovieModel(
                                id = it.id,
                                name = it.title ?: it.name,
                                desc = it.overview,
                                voteAverage = it.voteAverage,
                                posterUrl = BuildConfig.POSTER_PREFIX + it.posterPath,
                                type = type
                            )
                        )
                    } }
                    localDataSource.insertDatas(listItem)
                }

                override fun getKey(something: MovieModel): Int? = something.id

            }
        ) { localDataSource.getList(type) }.flow
    }

    override fun getDetail(type: String, id: Int): LiveData<MovieModel> = localDataSource.getDetail(type, id)

    @OptIn(ExperimentalPagingApi::class)
    override fun getFavorites(type: String): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            remoteMediator = object : NetworkMediator<MovieModel, Response>() {
                override suspend fun createCall(): Response {
                    /** This is Dummy Call, somehow the PagingSource
                     *  won't update without this dummy mediator
                     */
                    return Response()
                }

                override suspend fun saveCallResult(data: Response) { }

                override fun getKey(something: MovieModel): Int? = something.id
            },
            pagingSourceFactory = { localDataSource.getFavoriteList(type) }
        ).flow
    }

    override fun setFavorite(movieModel: MovieModel, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setFavorite(movieModel, state) }

}