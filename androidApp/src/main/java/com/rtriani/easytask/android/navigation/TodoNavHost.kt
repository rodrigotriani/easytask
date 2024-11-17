package com.rtriani.easytask.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rtriani.easytask.android.ui.feature.addedit.AddEditScreen
import com.rtriani.easytask.android.ui.feature.list.ListScreen
import com.rtriani.easytask.android.ui.feature.login.LoginScreen
import com.rtriani.easytask.android.ui.feature.profile.ProfileScreen
import kotlinx.serialization.Serializable


@Serializable
object LoginRoute

@Serializable
object ListRoute

@Serializable
data class AddEditRoute(val id: Long? = null)

@Serializable
object ProfileRoute

@Composable
fun TodoNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LoginRoute) {

        composable<LoginRoute> {
            LoginScreen(
                navigateToListScreen = {
                    navController.navigate(ListRoute)
                }
            )
        }

        composable<ListRoute> {
            ListScreen(
                navigateToAddEditScreen = { id ->
                    navController.navigate(AddEditRoute(id))
                },
                navigateToProfileScreen = {
                    navController.navigate(ProfileRoute)
                }
            )
        }

        composable<AddEditRoute> { backStackEntry ->
            val addEditRoute = backStackEntry.toRoute<AddEditRoute>()
            AddEditScreen(
                id = addEditRoute.id,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<ProfileRoute> { backStackEntry ->
            ProfileScreen()
        }
    }
}