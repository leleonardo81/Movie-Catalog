package com.bangkit.moviecatalog.ui.detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bangkit.moviecatalog.data.DataRepository
import com.bangkit.moviecatalog.data.source.local.entity.MovieModel
import com.bangkit.moviecatalog.utils.DataDummy
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var observer: Observer<MovieModel>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(dataRepository)
    }

    @Test
    fun getLoading() {
        val loading = viewModel.loading
        assertNotNull(loading)
        assertEquals(false, loading.value) // default value of loading
    }

    @Test
    fun fetchData() {
        val type = "movie"
        val id = 1224
        val detail = MutableLiveData<MovieModel>()
        detail.value = DataDummy.getListMovie()[0]

        Mockito.`when`(dataRepository.getDetail(type, id)).thenReturn(detail)
        val movieDetail = viewModel.fetchData(type, id)
        Mockito.verify(dataRepository).getDetail(type, id)
        assertNotNull(movieDetail)
        assertNotNull(movieDetail.value)
        assertEquals(detail.value?.name, movieDetail.value?.name)

        viewModel.fetchData(type, id).observeForever(observer)
        Mockito.verify(observer).onChanged(detail.value)
    }

    @Test
    fun getData() {
        assertNull(viewModel.data.value) // initial value of data
    }

    @Test
    fun setLoading() {
        viewModel.setLoading(true)
        assertNotNull(viewModel.loading)
        assertEquals(true, viewModel.loading.value)
    }

    @Test
    fun setData() {
        viewModel.setData(DataDummy.getListMovie()[0])
        assertNotNull(viewModel.data)
        assertEquals(DataDummy.getListMovie()[0], viewModel.data.value)
    }

    @Test
    fun setFavorite() {
        Mockito.doNothing().`when`(dataRepository).setFavorite(any(), eq(true))
        viewModel.setData(DataDummy.getListMovie()[0])
        viewModel.setFavorite(true)
        Mockito.verify(dataRepository).setFavorite(any(), eq(true))
    }
}