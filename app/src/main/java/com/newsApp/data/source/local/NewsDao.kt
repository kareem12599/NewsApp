package com.newsApp.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newsApp.data.News

@Dao
interface NewsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNews(news:List<News>)


    @Query("delete from news")
    suspend fun deleteAllNews()


    @Query("select * from news")
    suspend fun getAllNews():List<News>


    @Query("select *from news")
    fun observeTasks(): LiveData<List<News>>







}