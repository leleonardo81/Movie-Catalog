package com.bangkit.moviecatalog.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bangkit.moviecatalog.data.source.local.room.MovieTvDatabase
import com.bangkit.moviecatalog.utils.AppExecutors
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
abstract class NetworkMediator<ResultType : Any, RequestType>(private val mExecutors: AppExecutors) : RemoteMediator<Int, ResultType>(){
//    private val database = MovieTvDatabase.getInstance()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ResultType>
    ): MediatorResult {
//        val page = when (loadType) {
//            LoadType.REFRESH -> {
//                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//                remoteKeys?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
//            }
//            LoadType.PREPEND -> {
//                val remoteKeys = getRemoteKeyForFirstItem(state)
//                val prevKey = remoteKeys?.prevKey
//                if (prevKey == null) {
//                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                }
//                prevKey
//            }
//            LoadType.APPEND -> {
//                val remoteKeys = getRemoteKeyForLastItem(state)
//                val nextKey = remoteKeys?.nextKey
//                if (nextKey == null) {
//                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                }
//                nextKey
//            }
//        }

//        val apiQuery = query + IN_QUALIFIER

        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND ->{
                    Log.d("pernah", "Masuk")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    Log.d("peernahjd", state.pages.toString())
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )

                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.

                    getKey(lastItem)
                }
            }

            val apiResponse = createCall()

            MovieTvDatabase.getInstance()?.withTransaction {
                saveCallResult(apiResponse)
            }
//            mExecutors.diskIO().execute {
//                saveCallResult(apiResponse)
//            }

            return MediatorResult.Success(endOfPaginationReached = false)
        } catch (exception: IOException) {
            Log.e("ANA", exception.toString())
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e("ANA", exception.toString())
            return MediatorResult.Error(exception)
        }
    }

    protected abstract suspend fun createCall(): RequestType

    protected abstract suspend fun saveCallResult(data: RequestType)

    protected abstract fun getKey(something: ResultType): Int?


}