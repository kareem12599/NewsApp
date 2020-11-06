package com.newsApp.di

import android.content.Context
import com.newsApp.ui.MyNewsActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent

    }
    fun inject(newsActivity: MyNewsActivity)


}