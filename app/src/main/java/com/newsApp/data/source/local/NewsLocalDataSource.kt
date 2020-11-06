package com.newsApp.data.source.local

import com.newsApp.data.source.NewsDataSource
import com.newsApp.data.Result
import com.newsApp.data.News
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class NewsLocalDataSource @Inject constructor(
    val dao: NewsDao,
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NewsDataSource {


    override suspend fun getNews(): Result<List<News>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(dao.getAllNews())
        } catch (ex: Exception) {
            Result.Error(ex)
        }

    }


    override suspend fun deleteAllNews() = withContext(ioDispatcher) {
        dao.deleteAllNews()
    }

    override suspend fun saveNews(news: List<News>) = withContext(ioDispatcher) {
        dao.insertAllNews(news)

    }

}
