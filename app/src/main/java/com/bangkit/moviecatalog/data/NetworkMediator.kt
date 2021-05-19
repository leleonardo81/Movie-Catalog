package com.bangkit.moviecatalog.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bangkit.moviecatalog.data.source.local.room.MovieTvDatabase
import com.bangkit.moviecatalog.utils.EspressoIdlingResource
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
abstract class NetworkMediator<ResultType : Any, RequestType>() : RemoteMediator<Int, ResultType>(){
//    private val database = MovieTvDatabase.getInstance()

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
            val apiResponse = createCall()

            MovieTvDatabase.getInstance()?.withTransaction {
                saveCallResult(apiResponse)
            }

            EspressoIdlingResource.decrement()
            return MediatorResult.Success(endOfPaginationReached = false)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    protected abstract suspend fun createCall(): RequestType

    protected abstract suspend fun saveCallResult(data: RequestType)

    protected abstract fun getKey(something: ResultType): Int?


}