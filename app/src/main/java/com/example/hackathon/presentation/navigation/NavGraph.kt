package com.example.hackathon.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.hackathon.presentation.route.Route
import com.example.hackathon.presentation.screen.CreateCombinationScreen
import com.example.hackathon.presentation.screen.DetailScreen
import com.example.hackathon.presentation.screen.MyScreen
import com.example.hackathon.presentation.screen.home.screen.HomeScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
    ) {
        composable(route = Route.Home.route) {
            HomeScreen(
                modifier = modifier,
                onCombinationClick = { id ->
                    navController.navigate(Route.Detail.createRoute(id))
                },
                onCreateClick = {
                    navController.navigate(Route.Create.route)
                },
            )
        }
        composable(
            route = Route.Detail.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType }),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetailScreen(
                modifier = modifier,
                combinationId = id,
                onNavigateBack = { navController.popBackStack() },
            )
        }
        composable(route = Route.Create.route) {
            CreateCombinationScreen(
                modifier = modifier,
                onNavigateBack = { navController.popBackStack() },
            )
        }
        composable(route = Route.My.route) {
            MyScreen(
                modifier = modifier,
                onCombinationClick = { id ->
                    navController.navigate(Route.Detail.createRoute(id))
                },
            )
        }
    }
}
