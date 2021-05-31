package com.bangkit.moviecatalog.core.data

class DataRepositoryTest {

//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private val remote = Mockito.mock(RemoteDataSource::class.java)
//    private val local = Mockito.mock(LocalDataSource::class.java)
//    private val appExecutorRule = Mockito.mock(AppExecutors::class.java)
//    private val database = Mockito.mock(MovieTvDatabase::class.java)
//    private val dataRepository = DataRepository(remote, local, appExecutorRule, database)
//    private val pagingSource = Mockito.mock(PagingSource::class.java) as PagingSource<Int, MovieEntity>
//
//    private val type = "movie"
//    private val id = 1
//
//
//    @Test
//    fun getAll() {
//        runBlocking {
//            Mockito.`when`(local.getList(eq(type))).thenReturn(pagingSource)
//            dataRepository.getAll(type).firstOrNull()
//            Mockito.verify(local).getList(eq(type))
//        }
//    }
//
//    @Test
//    fun getDetail() {
//        Mockito.`when`(local.getDetail(type, id)).thenReturn(MutableLiveData(DataDummy.getListMovieEntity()[0]))
//        val movieDetail = LiveDataTestUtil.getValue(dataRepository.getDetail(type, id))
//        Mockito.verify(local).getDetail(eq(type), eq(id))
//        Assert.assertNotNull(movieDetail)
//        Assert.assertEquals(DataDummy.getListMovie()[0], movieDetail)
//    }
//
//    @Test
//    fun getFavorites() {
//        runBlocking {
//            Mockito.`when`(local.getFavoriteList(type)).thenReturn(pagingSource)
//            dataRepository.getFavorites(type).firstOrNull()
//            Mockito.verify(local).getFavoriteList(eq(type))
//        }
//    }
//
//    @Test
//    fun setFavorite() {
//        Mockito.`when`(appExecutorRule.diskIO()).thenReturn(Executor { it.run() })
//        dataRepository.setFavorite(DataDummy.getListMovie()[0], true)
//        Mockito.verify(local).setFavorite(DataDummy.getListMovieEntity()[0], true)
//    }
}