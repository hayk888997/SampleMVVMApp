package com.example.sampleappwithmvvm.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsListResponse {
    @SerializedName("results")
    @Expose
    var newsList: List<NewsResponse>? = null
}