package com.newsApp

import android.app.Application
import android.content.Context
import com.newsApp.di.DaggerAppComponent
import com.newsApp.ui.MyNewsActivity

class NewsApplication : Application() {
    private val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    companion object {
        @JvmStatic
        fun appComponent(context: Context) =
            (context.applicationContext as NewsApplication).appComponent
    }


}
fun MyNewsActivity.inject() {
    NewsApplication.appComponent(this).inject(this)
}