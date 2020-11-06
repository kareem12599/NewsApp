package com.newsApp.data.source.local

import com.newsApp.data.Result
import com.newsApp.data.News

interface NewsRepository {
   suspend  fun observeNews(): Result<List<News>>
    suspend fun refreshNews()
}
