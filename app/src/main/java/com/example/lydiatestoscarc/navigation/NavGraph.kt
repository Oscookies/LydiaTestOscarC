package com.example.lydiatestoscarc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.lydiatestoscarc.contactsList.domain.Contact
import com.example.lydiatestoscarc.contactDetails.ContactDetailsScreen
import com.example.lydiatestoscarc.contactsList.presentation.ContactsListScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {

    NavHost(navController, startDestination = Screen.ContactsList) {
        composable<Screen.ContactsList> {
            ContactsListScreen(onNavigateToDetails = { contact ->
                navController.navigate(Screen.ContactDetails(contact))
            })
        }
        composable<Screen.ContactDetails>(
            typeMap = mapOf(typeOf<Contact>() to ContactType())
        ) { backStackEntry ->
            val contactDetails: Screen.ContactDetails = backStackEntry.toRoute()
            ContactDetailsScreen(contactDetails.contact)
        }
    }
}

sealed interface Screen {

    @Serializable
    data object ContactsList: Screen

    @Serializable
    data class ContactDetails(val contact: Contact): Screen

}
