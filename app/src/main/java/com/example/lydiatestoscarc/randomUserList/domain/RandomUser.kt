package com.example.lydiatestoscarc.randomUserList.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class RandomUser (
    val gender: String,
    val name: Name,
    val email: String,
    var page: Int,
    var picture: Picture
): Parcelable

@Serializable
@Parcelize
data class Name (
    val first: String,
    val last: String
): Parcelable

@Serializable
@Parcelize
data class Picture (
    val medium: String
): Parcelable

