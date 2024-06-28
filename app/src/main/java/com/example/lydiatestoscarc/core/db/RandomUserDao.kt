package com.example.lydiatestoscarc.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RandomUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRandomUserList(randomUserList: List<RandomUserEntity>)

    @Query("SELECT * FROM RandomUserEntity WHERE page = :page_")
    suspend fun getRandomUserList(page_: Int): List<RandomUserEntity>

    @Query("SELECT * FROM RandomUserEntity WHERE page <= :page_")
    suspend fun getAllRandomUserList(page_: Int): List<RandomUserEntity>
}
