package com.example.lydiatestoscarc.contactsList.presentation

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.lydiatestoscarc.contactsList.domain.RandomUser

@Composable
fun RandomUserListScreen(
    onNavigateToDetails: (RandomUser) -> Unit
) {

    Text(
        text = "Contacts List",
        modifier = Modifier.clickable {
            onNavigateToDetails(RandomUser("John Doe"))
        }
    )

}