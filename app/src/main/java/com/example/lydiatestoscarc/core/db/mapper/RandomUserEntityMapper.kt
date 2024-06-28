package com.example.lydiatestoscarc.core.db.mapper

import com.example.lydiatestoscarc.contactsList.domain.Name
import com.example.lydiatestoscarc.contactsList.domain.Picture
import com.example.lydiatestoscarc.contactsList.domain.RandomUser
import com.example.lydiatestoscarc.core.db.RandomUserEntity

object RandomUserEntityMapper : EntityMapper<List<RandomUser>, List<RandomUserEntity>> {

    override fun asEntity(domain: List<RandomUser>): List<RandomUserEntity> {
        return domain.map { randomUser ->
            RandomUserEntity(
                page = randomUser.page,
                name = "${randomUser.name.first} ${randomUser.name.last}",
                gender = randomUser.gender,
                email = randomUser.email,
                picture = randomUser.picture.medium
            )
        }
    }

    override fun asDomain(entity: List<RandomUserEntity>): List<RandomUser> {
        return entity.map { randomUserEntity ->
            RandomUser(
                page = randomUserEntity.page,
                name = Name(randomUserEntity.name.substringBefore(' '), randomUserEntity.name.substringAfter(' ')),
                gender = randomUserEntity.gender,
                email = randomUserEntity.email,
                picture = Picture(randomUserEntity.picture),
            )
        }
    }
}

fun List<RandomUser>.asEntity(): List<RandomUserEntity> {
    return RandomUserEntityMapper.asEntity(this)
}

fun List<RandomUserEntity>?.asDomain(): List<RandomUser> {
    return RandomUserEntityMapper.asDomain(this.orEmpty())
}