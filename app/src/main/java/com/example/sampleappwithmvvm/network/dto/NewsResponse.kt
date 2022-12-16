package com.example.sampleappwithmvvm.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("id")
    @Expose
    var id: String? = null,

    @SerializedName("sectionName")
    @Expose
    var sectionName: String? = null,

    @SerializedName("webTitle")
    @Expose
    var webTitle: String? = null,

    @SerializedName("webUrl")
    @Expose
    var webUrl: String? = null,
)