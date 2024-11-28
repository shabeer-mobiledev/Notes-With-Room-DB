package com.shabeer.notesroomdb.room.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shabeer.notesroomdb.room.presantation.ui.DetailScreen
import com.shabeer.notesroomdb.room.presantation.ui.NoteScreen

@Composable
fun NavigationScreen(navHostController: NavHostController,searchQuery: String) {

    NavHost(navController = navHostController, startDestination = "home") {
        composable(route = "home") {
            NoteScreen (onClick = { title,description ->
                navHostController.navigate(route = "detailsScreen/$title/$description")
            }, searchQuery = searchQuery)
        }
        composable(route = "detailsScreen/{title}/{description}", arguments = listOf(
            navArgument("title") {
                type = NavType.StringType
            }, navArgument("description") {
                type = NavType.StringType
            }
        )) {backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""

            DetailScreen(title,description)
        }
    }
}