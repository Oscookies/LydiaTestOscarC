@file:Suppress("DEPRECATION")
package com.example.lydiatestoscarc.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.example.lydiatestoscarc.contactsList.domain.RandomUser
import com.google.gson.Gson

class ContactType : NavType<RandomUser>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): RandomUser? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): RandomUser {
        return Gson().fromJson(value, RandomUser::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: RandomUser) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: RandomUser): String {
        return Gson().toJson(value)
    }
}