package com.example.lydiatestoscarc.contactDetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.lydiatestoscarc.contactsList.domain.Contact

@Composable
fun ContactDetailsScreen(
    contact: Contact
) {

    Text(text = "Contact Details: ${contact.name}")

}