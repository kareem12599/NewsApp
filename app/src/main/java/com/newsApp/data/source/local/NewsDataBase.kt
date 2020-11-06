package com.newsApp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.newsApp.data.News


@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class NewsDataBase:RoomDatabase() {
    abstract fun newsDao(): NewsDao
}
const val DATABASE_NAME: String = "news.db"