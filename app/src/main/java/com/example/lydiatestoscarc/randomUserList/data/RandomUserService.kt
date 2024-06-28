package com.example.lydiatestoscarc.randomUserList.data

import com.example.lydiatestoscarc.randomUserList.domain.RandomUserNetworkResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserService {

    @GET("api/1.3/")
    suspend fun searchUsers(
        @Query("seed") seed: String,
        @Query("results") results: Int,
        @Query("page") page: Int
    ): ApiResponse<RandomUserNetworkResponse>

}