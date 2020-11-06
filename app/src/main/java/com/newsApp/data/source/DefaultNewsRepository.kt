package com.newsApp.data.source


import com.newsApp.data.News
import com.newsApp.data.source.local.NewsRepository
import com.newsApp.data.Result
import com.newsApp.di.AppModule.NewsLocalDataSource
import com.newsApp.di.AppModule.NewsRemoteDataSource
import javax.inject.Inject

class DefaultNewsRepository @Inject constructor(
    @NewsLocalDataSource private val localDataSource: NewsDataSource,
    @NewsRemoteDataSource private val remoteDataSource: NewsDataSource
) : NewsRepository {


    override suspend fun observeNews(): Result<List<News>> {
        return localDataSource.getNews()
    }


    private suspend fun updateNewsFromRemoteDataSource() {
        val remoteNews = remoteDataSource.getNews()
        if (remoteNews is Result.Success) {
            localDataSource.deleteAllNews()
            localDataSource.saveNews(remoteNews.data)
        } else {
            throw (remoteNews as Result.Error).exception
        }

    }

    override suspend fun refreshNews() {
        updateNewsFromRemoteDataSource()
    }
}