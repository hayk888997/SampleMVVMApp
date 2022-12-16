package com.example.sampleappwithmvvm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.example.sampleappwithmvvm.network.dto.NewsResponse
import com.example.sampleappwithmvvm.network.repo.NewsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import retrofit2.HttpException

class NewsSharedViewModel(
    private val repository: NewsRepository,
) : ViewModel(), KoinComponent {

    var newsList : List<NewsResponse>? = ArrayList()
    val stateFlow = MutableStateFlow<NewsListLoadState>(NewsListLoadState.Default)

    var chosenNews : NewsResponse? = null

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(this::class.java.simpleName, "", throwable)
        stateFlow.value = NewsListLoadState.Error(throwable as Exception)
    }

    init {
        fetchNewsList()
    }
    private fun fetchNewsList() {
        viewModelScope.launch(errorHandler) {
            stateFlow.value = NewsListLoadState.Loading
            try {
                newsList = repository.fetchNewsList()
                stateFlow.value = NewsListLoadState.Success
            } catch (ex: HttpException) {
                stateFlow.value = NewsListLoadState.Error(ex)
            }
        }
    }

    sealed class NewsListLoadState {
        object Default : NewsListLoadState()
        object Loading : NewsListLoadState()
        object Success : NewsListLoadState()
        class Error(val ex: Exception) : NewsListLoadState()
    }
}