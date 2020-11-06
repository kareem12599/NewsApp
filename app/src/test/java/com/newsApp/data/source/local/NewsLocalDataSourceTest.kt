package com.newsApp.data.source.local

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.newsApp.MainCoroutineRule
import com.newsApp.data.News
import com.newsApp.data.Result.*
import com.newsApp.data.source.NewsDataSource
import com.newsApp.data.succeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest

import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsLocalDataSourceTest {
    lateinit var localDataSource: NewsDataSource

    private val savedNews = mutableListOf(
        News(
            _id = 0,
            title = "title1",
            imageURL = " ",
            resourceName = "resourceName",
            newsLink = "news",
            resourceURL = ""
        ),
        News(
            _id = 1,
            title = "title2",
            imageURL = " ",
            resourceName = "resourceName",
            newsLink = "news",
            resourceURL = ""
        ),
        News(
            _id = 2,
            title = "title3",
            imageURL = " ",
            resourceName = "resourceName",
            newsLink = "news",
            resourceURL = ""
        ),
        News(
            _id = 3,
            title = "title4",
            imageURL = " ",
            resourceName = "resourceName",
            newsLink = "news",
            resourceURL = ""
        )

    )

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private lateinit var dao: NewsDao

    @Before
    fun setUp() {
        dao = mock()
        localDataSource = NewsLocalDataSource(dao, Dispatchers.Main)
    }

    @Test
    fun getNews_WhenCacheIsDirty_returnSuccess() = mainCoroutineRule.runBlockingTest {
        whenever(dao.getAllNews()).thenReturn(savedNews)

        val result = localDataSource.getNews()

        assertThat(result.succeeded, `is`(true))
        result as Success
        assertThat(result.data, `is`(savedNews.toList()))
    }

    @Test
    fun getNews_WhenCacheIsEmpty_returnEmptyData() = mainCoroutineRule.runBlockingTest {
        whenever(dao.getAllNews()).thenReturn(emptyList())

        val result = localDataSource.getNews()

        assertThat(result.succeeded, `is`(true))
        result as Success
        assertThat(result.data, `is`(emptyList()))

    }

    @Test
    fun getNews_WhenDeleteCache_returnEmptyData() = mainCoroutineRule.runBlockingTest {
        whenever(dao.getAllNews()).thenReturn(savedNews)
        whenever(dao.deleteAllNews()).then { savedNews.clear() }


        localDataSource.deleteAllNews()
        val result = localDataSource.getNews()


        assertThat(result.succeeded, `is`(true))
        result as Success
        assertThat(result.data, `is`(emptyList()))


    }

    @Test
    fun saveNews_WhenGetNews_returnsSameData() = mainCoroutineRule.runBlockingTest {
        val addedNews = News(
            _id = 4,
            title = "title1",
            imageURL = " ",
            resourceName = "resourceName",
            newsLink = "news",
            resourceURL = ""
        )
        whenever(dao.insertAllNews(listOf(addedNews)))
            .then { savedNews.add(addedNews) }
        whenever(dao.getAllNews()).thenReturn(savedNews)

        localDataSource.saveNews(listOf(addedNews))


        val result = localDataSource.getNews()
        assertThat(result.succeeded, `is`(true))
        result as Success
        assertEquals(addedNews._id, result.data.last()._id)

    }
}