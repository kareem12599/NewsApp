package com.newsApp.di

import android.content.Context
import android.content.res.AssetManager
import androidx.room.Room
import com.newsApp.data.source.FakeRemoteDataSource
import com.newsApp.data.source.NewsDataSource
import com.newsApp.data.source.local.DATABASE_NAME
import com.newsApp.data.source.local.NewsDataBase
import com.newsApp.data.source.local.NewsLocalDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class NewsRemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class NewsLocalDataSource

    @Singleton
    @Provides
    fun provideDataBase(context: Context) = Room.databaseBuilder(
        context.applicationContext,
        NewsDataBase::class.java,
        DATABASE_NAME
    )
        .build()

    @Singleton
    @Provides
    fun provideAssetManager(context: Context) = context.assets

    @Singleton
    @Provides
    @NewsRemoteDataSource
    fun provideFakeRemoteDataSource(assetManager: AssetManager): NewsDataSource {
      return  FakeRemoteDataSource(assetManager)
    }

    @Singleton
    @Provides
    @NewsLocalDataSource
    fun provideLocalDataSource(dataBase: NewsDataBase) : NewsDataSource {
       return  NewsLocalDataSource(dataBase.newsDao())
    }




}