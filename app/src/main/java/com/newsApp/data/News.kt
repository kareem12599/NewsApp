package com.newsApp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class NewsModel(val news : List<News>)

@Entity(tableName = "news")
data class News(
    @PrimaryKey(autoGenerate = true)
    var _id:Long,
    @SerializedName("title")var title: String,
    @SerializedName( "image_url")var imageURL: String,
    @SerializedName("resource_name")var resourceName: String,
    @SerializedName("resource_url")var resourceURL: String,
    @SerializedName("news_link")var newsLink: String
)