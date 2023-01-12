package com.example.sampleappwithmvvm.network.api

import com.example.sampleappwithmvvm.network.dto.Response
import retrofit2.http.*

interface NewsApi {
    @GET("search")
    suspend fun getNews(): Response
}