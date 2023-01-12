package com.example.sampleappwithmvvm.network

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceGenerator {

    companion object : KoinComponent {
        val cache: Cache by inject()
        val loggingInterceptor: HttpLoggingInterceptor by inject()
        val authInterceptor: AuthInterceptor by inject()
        val gson: GsonConverterFactory by inject()

        inline fun <reified T> createNonAuthorised(): T {
            val okHttpClient = OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .cache(cache)
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
                .callTimeout(REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gson)
                .build()

            return retrofit.create(T::class.java)
        }
    }
}

