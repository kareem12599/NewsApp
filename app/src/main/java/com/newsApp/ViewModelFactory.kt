package com.newsApp

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.newsApp.domain.NewsUseCase
import com.newsApp.ui.NewsViewModel
import java.lang.IllegalArgumentException
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val useCase: NewsUseCase,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {



    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = with(modelClass) {
        when {
            isAssignableFrom(NewsViewModel::class.java)
            -> NewsViewModel(useCase, handle)
            else -> throw IllegalArgumentException("Unknown class")

        } as T
    }
}