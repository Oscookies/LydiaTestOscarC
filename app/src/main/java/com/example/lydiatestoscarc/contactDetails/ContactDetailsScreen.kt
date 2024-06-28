package com.example.lydiatestoscarc.contactDetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.lydiatestoscarc.contactsList.domain.RandomUser

@Composable
fun ContactDetailsScreen(
    randomUser: RandomUser
) {

    Text(text = "Contact Details: ${randomUser.name}")

}