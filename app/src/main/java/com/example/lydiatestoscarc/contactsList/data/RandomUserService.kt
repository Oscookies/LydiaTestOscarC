package com.example.lydiatestoscarc.contactsList.data

import com.example.lydiatestoscarc.contactsList.domain.RandomUserNetworkResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserService {

    @GET("trip/search/v7")
    suspend fun searchUsers(
        @Query("seed") seed: String,
        @Query("results") results: Int,
        @Query("page") page: Int
    ): ApiResponse<RandomUserNetworkResponse>

}