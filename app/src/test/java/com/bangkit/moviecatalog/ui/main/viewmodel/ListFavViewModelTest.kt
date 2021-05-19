package com.bangkit.moviecatalog.ui.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.bangkit.moviecatalog.data.DataRepository
import com.bangkit.moviecatalog.data.source.local.entity.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ListFavViewModelTest {
    private lateinit var viewModel: ListFavViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var dataFlow: Flow<PagingData<MovieModel>>

    @Before
    fun setUp() {
        viewModel = ListFavViewModel(dataRepository)

    }

    @Test
    fun fetchMovie() {
        val type = "tv"
        runBlocking {
            Mockito.`when`(dataRepository.getAll(type)).thenReturn(dataFlow)
            viewModel.fetchMovie(type).firstOrNull()
            Mockito.verify(dataRepository).getAll(type)
        }
    }
}