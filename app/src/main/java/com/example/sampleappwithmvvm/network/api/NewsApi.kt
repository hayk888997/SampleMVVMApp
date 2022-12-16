package com.example.sampleappwithmvvm.network.api

import com.example.sampleappwithmvvm.network.dto.Response
import retrofit2.http.*

interface NewsApi {

    //TODO change api key place
    @GET("search?api-key=6a6ffd61-a67d-4503-a8ae-994dcbb7b9ea")
    suspend fun getNews(): Response
}