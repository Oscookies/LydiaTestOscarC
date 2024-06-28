package com.example.lydiatestoscarc.contactsList.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class RandomUser (
    val gender: String,
    val name: Name,
    val email: String,
    var page: Int
): Parcelable

@Serializable
@Parcelize
data class Name (
    val first: String,
    val last: String
): Parcelable

