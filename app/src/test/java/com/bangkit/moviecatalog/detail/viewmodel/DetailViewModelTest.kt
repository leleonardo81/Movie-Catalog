package com.bangkit.moviecatalog.detail.viewmodel

//import com.bangkit.moviecatalog.core.data.IDataRepository

//@RunWith(MockitoJUnitRunner::class)
//class DetailViewModelTest {
//
//    private lateinit var viewModel: DetailViewModel
//
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Mock
//    private lateinit var dataUseCase: MovieTvUseCase
//
//    @Mock
//    private lateinit var observer: Observer<MovieModel>
//
//    @Before
//    fun setUp() {
//        viewModel = DetailViewModel(dataUseCase)
//    }
//
//    @Test
//    fun getLoading() {
//        val loading = viewModel.loading
//        assertNotNull(loading)
//        assertEquals(false, loading.value) // default value of loading
//    }
//
//    @Test
//    fun fetchData() {
//        val type = "movie"
//        val id = 1224
//        val detail = DataDummy.getListMovie()[0]
//
//        Mockito.`when`(dataUseCase.getDetail(type, id)).thenReturn(flow { emit(detail) } )
//        val movieDetail = viewModel.fetchData(type, id)
//        Mockito.verify(dataUseCase).getDetail(type, id)
//        assertNotNull(movieDetail)
//        assertNotNull(movieDetail.value)
//        assertEquals(detail.name, movieDetail.value?.name)
//    }
//
//    @Test
//    fun getData() {
//        assertNull(viewModel.data.value) // initial value of data
//    }
//
//    @Test
//    fun setLoading() {
//        viewModel.setLoading(true)
//        assertNotNull(viewModel.loading)
//        assertEquals(true, viewModel.loading.value)
//    }
//
//    @Test
//    fun setData() {
//        viewModel.setData(DataDummy.getListMovie()[0])
//        assertNotNull(viewModel.data)
//        assertEquals(DataDummy.getListMovie()[0], viewModel.data.value)
//    }
//
//    @Test
//    fun setFavorite() {
//        Mockito.doNothing().`when`(dataRepository).setFavorite(any(), eq(true))
//        viewModel.setData(DataDummy.getListMovie()[0])
//        viewModel.setFavorite(true)
//        Mockito.verify(dataRepository).setFavorite(any(), eq(true))
//    }
//}