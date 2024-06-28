package com.example.lydiatestoscarc.core.di

import com.example.lydiatestoscarc.randomUserList.data.RandomUserRepository
import com.example.lydiatestoscarc.randomUserList.data.RandomUserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsAuthenticationRepo(
        randomUserRepositoryImpl: RandomUserRepositoryImpl
    ): RandomUserRepository

}