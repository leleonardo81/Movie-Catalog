package com.bangkit.moviecatalog.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bangkit.moviecatalog.core.data.source.local.room.MovieTvDatabase
import com.bangkit.moviecatalog.core.data.source.remote.api.ApiResponse
import com.bangkit.moviecatalog.core.utils.EspressoIdlingResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
abstract class NetworkMediator<ResultType : Any, RequestType>(private val database: MovieTvDatabase)
    : RemoteMediator<Int, ResultType>(){

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ResultType>
    ): MediatorResult {
        try {
            when (loadType) {
                LoadType.REFRESH -> { }
                LoadType.PREPEND ->{
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    getKey(lastItem)
                }
            }

            EspressoIdlingResource.increment()

            return when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    database.withTransaction {
                        saveCallResult(apiResponse.data)
                    }
                    EspressoIdlingResource.decrement()
                    MediatorResult.Success(endOfPaginationReached = false)
                }
                is ApiResponse.Empty -> {
                    EspressoIdlingResource.decrement()
                    MediatorResult.Success(endOfPaginationReached = true)
                }
                is ApiResponse.Error -> {
                    EspressoIdlingResource.decrement()
                    MediatorResult.Error(apiResponse.error)
                }
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    protected abstract fun getKey(something: ResultType): Int?


}