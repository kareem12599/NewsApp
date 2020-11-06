package com.newsApp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest

import com.newsApp.data.News
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class NewsDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var database: NewsDataBase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            NewsDataBase::class.java).build()
    }

    @ExperimentalCoroutinesApi
    fun insertNews_returnsGetAllNews() = runBlockingTest{
        val news = listOf(
            News(_id = 0, title = "title1", imageURL = "imageUrl ", resourceName = "resourceName", newsLink = "news", resourceURL = "")
        )
        database.newsDao().insertAllNews(news)
        val loadedNews = database.newsDao().getAllNews()


        assertThat(loadedNews, `is`(news))
    }

    @After
    fun closeDb() = database.close()
}
