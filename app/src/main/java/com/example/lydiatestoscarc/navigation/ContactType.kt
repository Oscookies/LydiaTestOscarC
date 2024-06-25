@file:Suppress("DEPRECATION")
package com.example.lydiatestoscarc.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.example.lydiatestoscarc.contactsList.domain.Contact
import com.google.gson.Gson

class ContactType : NavType<Contact>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Contact? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Contact {
        return Gson().fromJson(value, Contact::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Contact) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: Contact): String {
        return Gson().toJson(value)
    }
}