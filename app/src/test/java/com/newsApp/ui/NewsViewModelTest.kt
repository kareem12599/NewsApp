package com.newsApp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.newsApp.MainCoroutineRule
import com.newsApp.data.News
import com.newsApp.data.Result
import com.newsApp.domain.NewsUseCase
import com.newsApp.getOrAwaitValue
import com.newsApp.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule

@ExperimentalCoroutinesApi
class NewsViewModelTest {

    private lateinit var newsViewModel: NewsViewModel
    private var useCase: NewsUseCase = mock()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {

        newsViewModel = NewsViewModel(useCase, SavedStateHandle())
    }

    private val savedNews = listOf(
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
            title = "title4",
            imageURL = " ",
            resourceName = "resourceName",
            newsLink = "news",
            resourceURL = ""
        )

    )


    @Test
    fun getNews_whenNotRefresh_ReturnsCached() = mainCoroutineRule.runBlockingTest {
        whenever(useCase.observeNews()).thenReturn(Result.Success(savedNews))

        mainCoroutineRule.pauseDispatcher()
        newsViewModel.loadNews(false)
        newsViewModel.news.observeForTesting {
            assertThat(newsViewModel.dataLoading.value, `is`(false))

            mainCoroutineRule.resumeDispatcher()

            assertThat(newsViewModel.dataLoading.value, `is`(false))

            assertThat(newsViewModel.news.getOrAwaitValue().size, `is`(3))

        }


    }

    @Test
    fun getNews_whenObservingNews_dataLoaded() = mainCoroutineRule.runBlockingTest {
        whenever(useCase.observeNews()).thenReturn(Result.Success(savedNews))

        val liveData = newsViewModel.news.getOrAwaitValue {
            mainCoroutineRule.advanceUntilIdle()
        }
        assertThat(liveData, `is`(savedNews))
    }

    @Test
    fun refresh_WhenForceUpdate_DataLoadingIsTrue() {

        mainCoroutineRule.pauseDispatcher()

        newsViewModel.refresh()


        newsViewModel.news.observeForTesting {


            assertThat(newsViewModel.dataLoading.value, `is`(true))

            mainCoroutineRule.resumeDispatcher()

            assertThat(newsViewModel.dataLoading.value, `is`(false))
        }


    }

    @Test
    fun getNews_WhenSavedState_ReturnCached() = mainCoroutineRule.runBlockingTest {

        whenever(useCase.observeNews()).thenReturn(Result.Success(savedNews))
        val savedStateHandle = SavedStateHandle().also {
            it.set(NEWS_SAVED_STATE_KEY, false)
        }

        mainCoroutineRule.pauseDispatcher()
        newsViewModel = NewsViewModel(useCase, savedStateHandle)

        newsViewModel.news.observeForTesting {
            assertThat(newsViewModel.dataLoading.value, `is`(false))

            mainCoroutineRule.resumeDispatcher()

            assertThat(newsViewModel.dataLoading.value, `is`(false))

            assertThat(newsViewModel.news.getOrAwaitValue(), `is`(savedNews))

        }

    }


}