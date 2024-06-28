package com.example.lydiatestoscarc.di

import android.content.Context
import androidx.room.Room
import com.example.lydiatestoscarc.core.db.AppDatabase
import com.example.lydiatestoscarc.core.db.RandomUserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "lydia-database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideCallDao(appDatabase: AppDatabase): RandomUserDao {
        return appDatabase.randomUserDao()
    }
}