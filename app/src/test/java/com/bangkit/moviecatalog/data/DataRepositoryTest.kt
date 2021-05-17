package com.bangkit.moviecatalog.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bangkit.moviecatalog.data.source.remote.RemoteDataSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class DataRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val dataRepository = FakeDataRepository(remote)

    private val type = "movie"
    private val id = 1



    @Test
    fun getAll() {
        val movieList =  dataRepository.getAll(type)
        Mockito.verify(remote).getList(eq(type), any())
        Assert.assertNotNull(movieList)
    }

    @Test
    fun getDetail() {
        val movieDetail = dataRepository.getDetail(type, id)
        Mockito.verify(remote).getDetail(eq(type), eq(id), any())
        Assert.assertNotNull(movieDetail)
    }
}