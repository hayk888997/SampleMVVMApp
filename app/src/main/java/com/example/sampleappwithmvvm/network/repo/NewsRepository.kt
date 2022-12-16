package com.example.sampleappwithmvvm.network.repo

import com.example.sampleappwithmvvm.network.api.NewsApi
import com.example.sampleappwithmvvm.network.dto.NewsResponse

class NewsRepository(
    private val newsApi: NewsApi
) {
    suspend fun fetchNewsList(): List<NewsResponse>? {
        val response = newsApi.getNews()
        return response.newsListResponse?.newsList
    }
}