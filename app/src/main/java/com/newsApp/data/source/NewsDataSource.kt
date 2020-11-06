package com.newsApp.data.source

import com.newsApp.data.News
import com.newsApp.data.Result


interface NewsDataSource {
    suspend fun getNews(): Result<List<News>>
    suspend fun deleteAllNews()
    suspend fun saveNews(news: List<News>)
}