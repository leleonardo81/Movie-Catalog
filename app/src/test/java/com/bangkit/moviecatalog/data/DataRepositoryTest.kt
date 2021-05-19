package com.bangkit.moviecatalog.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import com.bangkit.moviecatalog.data.source.local.LocalDataSource
import com.bangkit.moviecatalog.data.source.local.entity.MovieModel
import com.bangkit.moviecatalog.data.source.remote.RemoteDataSource
import com.bangkit.moviecatalog.utils.AppExecutors
import com.bangkit.moviecatalog.utils.DataDummy
import com.bangkit.moviecatalog.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.eq
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.util.concurrent.Executor

class DataRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutorRule = Mockito.mock(AppExecutors::class.java)
    private val dataRepository = FakeDataRepository(remote, local, appExecutorRule)
    private val pagingSource = Mockito.mock(PagingSource::class.java) as PagingSource<Int, MovieModel>

    private val type = "movie"
    private val id = 1


    @Test
    fun getAll() {
        runBlocking {
            Mockito.`when`(local.getList(eq(type))).thenReturn(pagingSource)
            dataRepository.getAll(type).firstOrNull()
            Mockito.verify(local).getList(eq(type))
        }
    }

    @Test
    fun getDetail() {
        Mockito.`when`(local.getDetail(type, id)).thenReturn(MutableLiveData(DataDummy.getListMovie()[0]))
        val movieDetail = LiveDataTestUtil.getValue(dataRepository.getDetail(type, id))
        Mockito.verify(local).getDetail(eq(type), eq(id))
        Assert.assertNotNull(movieDetail)
        Assert.assertEquals(DataDummy.getListMovie()[0], movieDetail)
    }

    @Test
    fun getFavorites() {
        runBlocking {
            Mockito.`when`(local.getFavoriteList(type)).thenReturn(pagingSource)
            dataRepository.getFavorites(type).firstOrNull()
            Mockito.verify(local).getFavoriteList(eq(type))
        }
    }

    @Test
    fun setFavorite() {
        Mockito.`when`(appExecutorRule.diskIO()).thenReturn(Executor { it.run() })
        dataRepository.setFavorite(DataDummy.getListMovie()[0], true)
        Mockito.verify(local).setFavorite(DataDummy.getListMovie()[0], true)
    }
}