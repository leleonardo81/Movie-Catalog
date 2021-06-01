package com.bangkit.moviecatalog.main.viewmodel

//import com.bangkit.moviecatalog.core.data.IDataRepository

//@RunWith(MockitoJUnitRunner::class)
//class ListViewModelTest {

//    private lateinit var viewModel: ListViewModel
//
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Mock
//    private lateinit var dataRepository: IDataRepository
//
//    @Mock
//    private lateinit var dataFlow: Flow<PagingData<MovieModel>>
//
//    @Before
//    fun setUp() {
//        viewModel = ListViewModel(dataRepository)
//    }
//
//    @Test
//    fun fetchMovie() {
//        val type = "movie"
//        runBlocking {
//            Mockito.`when`(dataRepository.getAll(type)).thenReturn(dataFlow)
//            viewModel.fetchMovie(type).firstOrNull()
//            Mockito.verify(dataRepository).getAll(type)
//        }
//    }
//}