package com.example.sampleappwithmvvm.viewmodel

import com.example.sampleappwithmvvm.network.REQUEST_TIMEOUT

import com.example.sampleappwithmvvm.network.api.NewsApi
import com.example.sampleappwithmvvm.network.repo.NewsRemoteRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext
import org.koin.dsl.module
import org.koin.test.junit5.AutoCloseKoinTest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals

internal class NewsSharedViewModelTest : AutoCloseKoinTest() {
    val mockWebServer = MockWebServer()

    val mod = module {
        single {
            val client = OkHttpClient.Builder()
                .build()
            Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .client(client)
                .build()
        }
    }

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    val retrofit: Retrofit by inject()

    @ExperimentalCoroutinesApi
    @Test
    fun `test NEWS_VIWE_MODEL's states's SUCCESS with 1 object `() = runTest {
        GlobalContext.startKoin {
            modules(mod)
        }

        val testDataJsonWithOneElement = "{\n" +
                "  \"response\": {\n" +
                "    \"results\": [\n" +
                "      {\n" +
                "        \"id\": \"commentisfree/2023/jan/12/tories-anti-strike-bill-greater-industrial-upheaval\",\n" +
                "        \"type\": \"article\",\n" +
                "        \"sectionId\": \"commentisfree\",\n" +
                "        \"sectionName\": \"Opinion\",\n" +
                "        \"webPublicationDate\": \"2023-01-12T06:00:07Z\",\n" +
                "        \"webTitle\": \"The Tories’ anti-strike bill will only lead to even greater industrial upheaval | Martin Kettle\",\n" +
                "        \"webUrl\": \"https://www.theguardian.com/commentisfree/2023/jan/12/tories-anti-strike-bill-greater-industrial-upheaval\",\n" +
                "        \"apiUrl\": \"https://content.guardianapis.com/commentisfree/2023/jan/12/tories-anti-strike-bill-greater-industrial-upheaval\",\n" +
                "        \"isHosted\": false,\n" +
                "        \"pillarId\": \"pillar/opinion\",\n" +
                "        \"pillarName\": \"Opinion\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}"

        val successResponse = MockResponse().setBody(testDataJsonWithOneElement)
        val api = retrofit.create(NewsApi::class.java)
        mockWebServer.enqueue(successResponse)
        val repository = NewsRemoteRepository(api)

        val viewModel = NewsSharedViewModel(repository)
        runBlocking {
            delay(REQUEST_TIMEOUT)
        }

        val testSuccessStateFlow = viewModel.stateFlow.value
        assertThat(
            testSuccessStateFlow,
            instanceOf(NewsSharedViewModel.NewsListLoadState.Success::class.java)
        )
        assertEquals(viewModel.newsList?.size, 1)
        assertEquals(viewModel.newsList?.get(0)?.id, "commentisfree/2023/jan/12/tories-anti-strike-bill-greater-industrial-upheaval" )
        assertEquals(viewModel.newsList?.get(0)?.sectionName, "Opinion" )
        assertEquals(viewModel.newsList?.get(0)?.webTitle, "The Tories’ anti-strike bill will only lead to even greater industrial upheaval | Martin Kettle" )
        assertEquals(viewModel.newsList?.get(0)?.webUrl, "https://www.theguardian.com/commentisfree/2023/jan/12/tories-anti-strike-bill-greater-industrial-upheaval" )
    }
}