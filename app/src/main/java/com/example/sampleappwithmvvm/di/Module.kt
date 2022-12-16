package com.example.sampleappwithmvvm.di

import com.example.sampleappwithmvvm.viewmodel.NewsSharedViewModel
import com.example.sampleappwithmvvm.network.ServiceGenerator
import com.example.sampleappwithmvvm.network.api.NewsApi
import com.example.sampleappwithmvvm.network.repo.NewsRepository
import com.sample.appwithmvvm.BuildConfig
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory

val koinModule = module {
    val cacheSize = Runtime.getRuntime().maxMemory() / 8

    //viewModels
    viewModel { NewsSharedViewModel(get()) }


    //Repositories
    single { NewsRepository(get()) }


    single { GsonConverterFactory.create() }
    single {
        ServiceGenerator.createNonAuthorised<NewsApi>()
    }
    single {
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        )
    }
    factory { Cache(androidContext().cacheDir, cacheSize) }




}