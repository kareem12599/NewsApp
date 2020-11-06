package com.newsApp.domain


import com.newsApp.data.News
import com.newsApp.data.Result
import com.newsApp.data.source.DefaultNewsRepository
import javax.inject.Inject

class NewsUseCase @Inject constructor( val newsRepository: DefaultNewsRepository) {


    suspend fun refresh() {
        newsRepository.refreshNews()

    }

    suspend fun observeNews(): Result<List<News>> {
        return newsRepository.observeNews()
    }


}