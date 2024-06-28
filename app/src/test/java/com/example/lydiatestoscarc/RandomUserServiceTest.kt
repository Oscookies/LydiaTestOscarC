package com.example.lydiatestoscarc

import com.example.lydiatestoscarc.randomUserList.data.RandomUserService
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import com.skydoves.sandwich.toFlow
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.net.SocketTimeoutException

@OptIn(ExperimentalCoroutinesApi::class)
class RandomUserServiceTest {

    @get:Rule
    val mockWebServer = MockWebServer()

    val testScope = TestScope(UnconfinedTestDispatcher())
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create(testScope))
            .build()
    }

    private val randomUserService by lazy {
        retrofit.create(RandomUserService::class.java)
    }

    @Test
    fun getUsersSuccess() = runTest {
        mockWebServer.enqueue(MockResponse().setBody(getRandomUsersSuccess).setResponseCode(200))
        val result = randomUserService.searchUsers("",2,1)
        assert(result.toFlow().first().results.size == 2)
    }

    @Test
    fun getUserTimeout() {
        mockWebServer.enqueue(MockResponse().setBody("").setResponseCode(408))
        runBlocking {
            try {
                randomUserService.searchUsers("",2,1)
            }
            catch (e: Exception){
                assert(e is SocketTimeoutException)
            }

        }
    }
}

const val getRandomUsersSuccess =
"""
{
    "results": [
        {
            "gender": "female",
            "name": {
                "title": "Mrs",
                "first": "Frances",
                "last": "Herrera"
            },
            "location": {
                "street": {
                    "number": 5740,
                    "name": "W Dallas St"
                },
                "city": "Wollongong",
                "state": "Australian Capital Territory",
                "country": "Australia",
                "postcode": 3018,
                "coordinates": {
                    "latitude": "-83.6031",
                    "longitude": "18.6994"
                },
                "timezone": {
                    "offset": "+2:00",
                    "description": "Kaliningrad, South Africa"
                }
            },
            "email": "frances.herrera@example.com",
            "login": {
                "uuid": "56989ef1-ee6a-4d6b-a20f-18b343213437",
                "username": "yellowbutterfly577",
                "password": "8989",
                "salt": "3zM1zTzh",
                "md5": "b0a240ad4aea0d1a4ef28339ed246c3f",
                "sha1": "32c3d0001a4379945544c56a8ef92f98dd6891b4",
                "sha256": "a5ed6f0b69b91ba8c444caf4b881f853fd5d36e6dfa68eddb11bebe21304b455"
            },
            "dob": {
                "date": "1983-05-27T11:26:06.318Z",
                "age": 41
            },
            "registered": {
                "date": "2006-01-06T13:34:20.469Z",
                "age": 18
            },
            "phone": "02-1122-1470",
            "cell": "0444-339-924",
            "id": {
                "name": "TFN",
                "value": "174195871"
            },
            "picture": {
                "large": "https://randomuser.me/api/portraits/women/27.jpg",
                "medium": "https://randomuser.me/api/portraits/med/women/27.jpg",
                "thumbnail": "https://randomuser.me/api/portraits/thumb/women/27.jpg"
            },
            "nat": "AU"
        },
        {
            "gender": "male",
            "name": {
                "title": "Mr",
                "first": "Juliano",
                "last": "da Mota"
            },
            "location": {
                "street": {
                    "number": 71,
                    "name": "Rua Treze "
                },
                "city": "Passos",
                "state": "Acre",
                "country": "Brazil",
                "postcode": 48678,
                "coordinates": {
                    "latitude": "43.6207",
                    "longitude": "-159.4352"
                },
                "timezone": {
                    "offset": "-11:00",
                    "description": "Midway Island, Samoa"
                }
            },
            "email": "juliano.damota@example.com",
            "login": {
                "uuid": "7745ef46-2c62-4e9e-b241-29400da064bc",
                "username": "lazypanda146",
                "password": "rocket",
                "salt": "pTWQ3mzu",
                "md5": "5830c974e8bc8537d6d527f3c270ed10",
                "sha1": "f55d9a750b0bcb0ce9d22ed60120eaee811ffb35",
                "sha256": "8d9a7c0e1eb67b38744bd111b64ab47a6b2029027fd514ff8b74153d5d2598fa"
            },
            "dob": {
                "date": "1992-03-28T11:45:34.772Z",
                "age": 32
            },
            "registered": {
                "date": "2019-02-17T07:29:54.396Z",
                "age": 5
            },
            "phone": "(63) 5577-5851",
            "cell": "(71) 1434-8746",
            "id": {
                "name": "",
                "value": null
            },
            "picture": {
                "large": "https://randomuser.me/api/portraits/men/88.jpg",
                "medium": "https://randomuser.me/api/portraits/med/men/88.jpg",
                "thumbnail": "https://randomuser.me/api/portraits/thumb/men/88.jpg"
            },
            "nat": "BR"
        }
    ],
    "info": {
        "seed": "lydia",
        "results": 2,
        "page": 1,
        "version": "1.3"
    }
}
"""