package com.bangkit.moviecatalog.ui.main.viewmodel

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
class ListViewModelTest {

    private lateinit var viewModel: ListViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var observer: Observer<List<MovieModel>>

    @Before
    fun setUp() {
        viewModel = ListViewModel(dataRepository)
    }

    @Test
    fun getLoading() {
        val loading = viewModel.loading
        assertNotNull(loading)
        assertEquals(false, loading.value) // default value of loading
    }

    @Test
    fun fetchMovie() {
        val type = "movie"
        val listMovie = MutableLiveData<List<MovieModel>>()
        listMovie.value = DataDummy.getListMovie()

        Mockito.`when`(dataRepository.getAll(type)).thenReturn(listMovie)
        val movieList = viewModel.fetchMovie(type)
        Mockito.verify(dataRepository).getAll(type)
        assertNotNull(movieList)
        assertNotNull(movieList.value)
        assertEquals(listMovie.value?.size, movieList.value?.size)

        viewModel.fetchMovie(type).observeForever(observer)
        Mockito.verify(observer).onChanged(listMovie.value)

    }
}