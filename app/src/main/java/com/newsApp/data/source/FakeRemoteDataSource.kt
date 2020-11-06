package com.newsApp.data.source

import android.content.res.AssetManager
import com.google.gson.Gson
import com.newsApp.data.News
import com.newsApp.data.NewsModel
import com.newsApp.data.Result
import javax.inject.Inject

class FakeRemoteDataSource @Inject constructor(private val assetManager: AssetManager) :
    NewsDataSource {


    override suspend fun getNews(): Result<List<News>> {
        val newsFakeData =
            Gson().fromJson(assetManager.readAssetsFile("news.json"), NewsModel::class.java).news
        return if (newsFakeData.isNotEmpty()) {
            Result.Success(newsFakeData)
        } else
            Result.Error(Exception("Could not find the news"))
    }


    override suspend fun deleteAllNews() {
        TODO("Not yet implemented")
    }

    override suspend fun saveNews(news: List<News>) {
        TODO("Not yet implemented")
    }


    private fun AssetManager.readAssetsFile(fileName: String): String =
        open(fileName).bufferedReader().use { it.readText() }

}