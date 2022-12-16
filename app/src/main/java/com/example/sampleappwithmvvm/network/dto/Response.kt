package com.example.sampleappwithmvvm.network.dto

import com.google.gson.annotations.SerializedName

class Response {
    @SerializedName("response")
    var newsListResponse: NewsListResponse? = null
}