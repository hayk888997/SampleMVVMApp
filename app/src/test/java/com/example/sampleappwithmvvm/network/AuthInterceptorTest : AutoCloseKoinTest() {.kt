package com.example.sampleappwithmvvm.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import org.koin.test.junit5.AutoCloseKoinTest
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

internal class AuthInterceptorTest : AutoCloseKoinTest() {

    val mockWebServer = MockWebServer()

    val mod = module {
        single {
            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .build()

            Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .client(client)
                .build()
        }
    }

    val retrofit: Retrofit by inject()

    @Test
    fun `test AUTH_INTERCEPTOR is added as Query`() {
        startKoin {
            modules(mod)
        }
        val successResponse = MockResponse()
        val api = retrofit.create(TestApi::class.java)
        mockWebServer.enqueue(successResponse)
        api.test().execute()
        val request = mockWebServer.takeRequest()
        val authQuery = request.requestUrl?.queryParameter("api-key")
        assertThat(authQuery, `is`(NEWS_TOKEN))
    }
}

private interface TestApi {
    @GET("/test")
    fun test(): Call<Unit>
}