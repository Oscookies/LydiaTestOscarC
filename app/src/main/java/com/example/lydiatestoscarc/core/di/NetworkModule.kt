package com.example.lydiatestoscarc.core.di

import com.example.lydiatestoscarc.BuildConfig
import com.example.lydiatestoscarc.randomUserList.data.RandomUserService
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val timeOut = 20L

    @Provides
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                readTimeout(timeOut, TimeUnit.SECONDS)
                connectTimeout(timeOut, TimeUnit.SECONDS)
            }
            .build()
    }

    @Provides
    fun provideGsonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.API_BASE_URL)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun provideAuthApi(retrofit: Retrofit): RandomUserService {
        return retrofit.create(RandomUserService::class.java)
    }

}