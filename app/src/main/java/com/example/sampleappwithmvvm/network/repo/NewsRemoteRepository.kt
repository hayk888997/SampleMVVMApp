package com.example.sampleappwithmvvm.network.repo

import com.example.sampleappwithmvvm.network.api.NewsApi
import com.example.sampleappwithmvvm.network.dto.NewsItemResponse

class NewsRemoteRepository(
    private val newsApi: NewsApi
) {
    // TODO: We do use newsItemResponse in whole project as it has exact
    //  information we need for the app and there is no need for the mapper
    //  in this scenario. In other scenario mapper should be implemented to
    //  create object required for local usage
    suspend fun fetchNewsList(): List<NewsItemResponse>? {
        val response = newsApi.getNews()
        return response.newsListResponse?.newsList
    }
}