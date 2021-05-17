package com.bangkit.moviecatalog.ui.detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bangkit.moviecatalog.data.source.local.entity.MovieModel
import com.bangkit.moviecatalog.data.DataRepository
import com.bangkit.moviecatalog.utils.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
}