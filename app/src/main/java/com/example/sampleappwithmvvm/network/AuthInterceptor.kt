package com.example.sampleappwithmvvm.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    //TODO NEWS_TOKEN is hardcoded now, as it has long enough expiration time for sample.
    // In real life token refreshing functionality should be implemented
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder().addQueryParameter("api-key", NEWS_TOKEN).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}