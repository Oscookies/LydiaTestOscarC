package com.example.lydiatestoscarc.core.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RandomUserEntity(
    @PrimaryKey
    val email: String,
    val gender: String,
    val name: String,
    var page: Int = 0,
)