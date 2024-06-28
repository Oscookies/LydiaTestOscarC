package com.example.lydiatestoscarc.randomUserList.data

import com.example.lydiatestoscarc.randomUserList.domain.RandomUser
import com.example.lydiatestoscarc.core.db.RandomUserDao
import com.example.lydiatestoscarc.core.db.mapper.asDomain
import com.example.lydiatestoscarc.core.db.mapper.asEntity
import com.example.lydiatestoscarc.core.di.Dispatcher
import com.example.lydiatestoscarc.core.di.LydiaDispatchers
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

interface RandomUserRepository {

    suspend fun search(
        page: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ): Flow<List<RandomUser>>
}

class RandomUserRepositoryImpl @Inject constructor(
    private val randomUserService: RandomUserService,
    private val randomUserDao: RandomUserDao,
    @Dispatcher(LydiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : RandomUserRepository {

    private val seed = "lydia"
    private val pageCount = 20

    override suspend fun search(
        page: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) = flow {
        var users = randomUserDao.getRandomUserList(page).asDomain()
        if (users.isEmpty()) {
            val response = randomUserService.searchUsers(seed, pageCount, page)
            response.suspendOnSuccess {
                users = data.results
                users.forEach { user -> user.page = page }
                randomUserDao.insertRandomUserList(users.asEntity())
                emit(randomUserDao.getAllRandomUserList(page).asDomain())
            }.onFailure { // handles the all error cases from the API request fails.
                onError(message())
            }
        } else {
            emit(randomUserDao.getAllRandomUserList(page).asDomain())
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

}