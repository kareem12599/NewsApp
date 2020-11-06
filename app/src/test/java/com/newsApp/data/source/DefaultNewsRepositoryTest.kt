package com.newsApp.data.source

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.newsApp.MainCoroutineRule
import com.newsApp.data.News
import com.newsApp.data.Result
import com.newsApp.data.source.local.NewsDao
import com.newsApp.data.source.local.NewsLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultNewsRepositoryTest{

    private lateinit var remoteDataSource: NewsDataSource
    private lateinit var localDataSource: NewsDataSource
    private lateinit var repository: DefaultNewsRepository






    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp(){
        remoteDataSource = mock()

        val dao : NewsDao= mock()
        localDataSource = NewsLocalDataSource(dao, Dispatchers.Main)

        repository = DefaultNewsRepository(localDataSource, remoteDataSource)
    }
    @Test
    fun observeNews_emptySource_ReturnsSuccess() = mainCoroutineRule.runBlockingTest{
        val emptyNews = emptyList<News>()
        whenever(remoteDataSource.getNews()).thenReturn(Result.Success(emptyNews))

        whenever(localDataSource.getNews()).thenReturn(Result.Success(emptyNews))

        repository.refreshNews()

        assertTrue(repository.observeNews() is Result.Success)

    }


}