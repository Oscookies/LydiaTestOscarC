package com.example.lydiatestoscarc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.lydiatestoscarc.randomUserDetails.RandomUserDetailsScreen
import com.example.lydiatestoscarc.randomUserList.presentation.RandomUserListScreen
import kotlinx.serialization.Serializable

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {

    NavHost(navController, startDestination = Screen.RandomUserList) {
        composable<Screen.RandomUserList> {
            RandomUserListScreen(onNavigateToDetails = { user ->
                navController.navigate(
                    Screen.RandomUserDetails(
                        "${user.name.first} ${user.name.last}",
                        user.email,
                        user.picture.medium
                    )
                )
            })
        }
        composable<Screen.RandomUserDetails>(
        ) { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.RandomUserDetails>()
            RandomUserDetailsScreen(
                args.name,
                args.email,
                args.imageUrl
            )
        }
    }
}

sealed interface Screen {

    @Serializable
    data object RandomUserList: Screen

    @Serializable
    data class RandomUserDetails(
        val name: String,
        val email: String,
        val imageUrl: String
    ): Screen

}
