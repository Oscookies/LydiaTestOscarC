package com.example.lydiatestoscarc

import com.example.lydiatestoscarc.core.db.RandomUserDao
import com.example.lydiatestoscarc.core.db.mapper.asEntity
import com.example.lydiatestoscarc.randomUserList.data.RandomUserRepository
import com.example.lydiatestoscarc.randomUserList.data.RandomUserRepositoryImpl
import com.example.lydiatestoscarc.randomUserList.data.RandomUserService
import com.example.lydiatestoscarc.randomUserList.domain.Name
import com.example.lydiatestoscarc.randomUserList.domain.Picture
import com.example.lydiatestoscarc.randomUserList.domain.RandomUser
import com.example.lydiatestoscarc.randomUserList.domain.RandomUserNetworkResponse
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class RandomUserRepositoryTest {

    private lateinit var userService: RandomUserService
    private lateinit var randomUserRepository: RandomUserRepository
    private lateinit var randomUserDao: RandomUserDao

    val listOfUsers = listOf(RandomUser("non binary", Name("John","B"),"a@a.com", 1, Picture("picturl")))

    @Before
    fun setup() {
        userService = Mockito.mock(RandomUserService::class.java)
        randomUserDao = Mockito.mock(RandomUserDao::class.java)
        randomUserRepository = RandomUserRepositoryImpl(userService, randomUserDao, UnconfinedTestDispatcher())
    }

    @Test
    fun getUsersFromDBEmptyAndApiSuccess() = runTest {
        Mockito.`when`(userService.searchUsers("lydia", 20, 1)).thenReturn(
            ApiResponse.of { RandomUserNetworkResponse(listOfUsers) }
        )
        Mockito.`when`(randomUserDao.getRandomUserList(1)).thenReturn(
            emptyList()
        )
        Mockito.`when`(randomUserDao.getAllRandomUserList(1)).thenReturn(
            listOfUsers.asEntity()
        )

        val flowEmissionList = randomUserRepository.search(1, {}, {}, {}).toList()
        assert(flowEmissionList[0] == listOfUsers)
    }

    @Test
    fun getUsersFromDBEmptyAndApiFailure() = runTest {
        Mockito.`when`(userService.searchUsers("lydia", 20, 1)).thenReturn(
            ApiResponse.exception(Exception())
        )
        Mockito.`when`(randomUserDao.getRandomUserList(1)).thenReturn(
            emptyList()
        )

        Mockito.verify(randomUserDao, Mockito.times(0)).insertRandomUserList(anyList())
    }

    @Test
    fun getUsersFromDBSuccess() = runTest {
        Mockito.`when`(randomUserDao.getRandomUserList(1)).thenReturn(
            listOfUsers.asEntity()
        )
        Mockito.`when`(randomUserDao.getAllRandomUserList(1)).thenReturn(
            listOfUsers.asEntity()
        )

        val flowEmissionList = randomUserRepository.search(1, {}, {}, {}).toList()
        assert(flowEmissionList[0] == listOfUsers)
    }

}