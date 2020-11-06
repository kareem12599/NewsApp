package com.newsApp.ui

import androidx.lifecycle.*
import com.newsApp.data.News
import com.newsApp.data.Result
import com.newsApp.domain.NewsUseCase

class NewsViewModel constructor(
     val newsUseCase: NewsUseCase,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _forceUpdate = MutableLiveData<Boolean>(false)
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    init {
        loadNews(getSavedState())
    }

    var news: LiveData<List<News>> =
        _forceUpdate.switchMap { forceUpdate ->
            _dataLoading.value = forceUpdate
            liveData(context = viewModelScope.coroutineContext) {
                if (forceUpdate) {
                    refreshNews()
                    _dataLoading.value = false

                }

                val result = newsUseCase.observeNews()
                if (result is Result.Success) {
                    emit(result.data)
                } else {
                    emit(emptyList())
                }
            }
        }

    private suspend fun refreshNews() {
        newsUseCase.refresh()
        savedStateHandle.set(NEWS_SAVED_STATE_KEY, false)
    }


     fun loadNews(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate
    }

    fun refresh() {
        _forceUpdate.value = true
    }
    private fun getSavedState() : Boolean {
        return savedStateHandle.get(NEWS_SAVED_STATE_KEY) ?: true
    }
}
const val NEWS_SAVED_STATE_KEY = "news_state"