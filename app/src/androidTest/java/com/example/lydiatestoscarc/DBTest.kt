package com.example.lydiatestoscarc

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lydiatestoscarc.core.db.AppDatabase
import com.example.lydiatestoscarc.core.db.RandomUserDao
import com.example.lydiatestoscarc.core.db.mapper.asEntity
import com.example.lydiatestoscarc.randomUserList.domain.Name
import com.example.lydiatestoscarc.randomUserList.domain.Picture
import com.example.lydiatestoscarc.randomUserList.domain.RandomUser
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DBTest {
    private lateinit var randomUserDao: RandomUserDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        randomUserDao = db.randomUserDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUsersToDbGetsUsersByPage() = runBlocking {
        val user1 = RandomUser("non binary", Name("John","B"),"a@a.com", 1, Picture("picturl"))
        val user2 = RandomUser("non binary", Name("John","B"),"a@a.com", 10, Picture("picturl"))
        val userList = listOf(user1,user2).asEntity()
        randomUserDao.insertRandomUserList(userList)
        val usersPage1 = randomUserDao.getRandomUserList(1)
        val usersPage10 = randomUserDao.getRandomUserList(10)
        assert(usersPage1.size == 1)
        assert(usersPage10.size == 1)
    }
}