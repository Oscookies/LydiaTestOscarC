package com.example.lydiatestoscarc.contactsList.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Contact (
    val name: String
): Parcelable

