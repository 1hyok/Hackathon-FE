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
import com.example.hackathon.presentation.screen.EditProfileScreen
import com.example.hackathon.presentation.screen.LoginScreen
import com.example.hackathon.presentation.screen.MyScreen
import com.example.hackathon.presentation.screen.RegistrationScreen
import com.example.hackathon.presentation.screen.SearchScreen
import com.example.hackathon.presentation.screen.SplashScreen
import com.example.hackathon.presentation.screen.home.screen.HomeScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Route.Splash.route,
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
        composable(route = Route.Search.route) {
            SearchScreen(
                modifier = modifier,
                onNavigateBack = { navController.popBackStack() },
                onCombinationClick = { id ->
                    navController.navigate(Route.Detail.createRoute(id))
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
                onEditProfileClick = {
                    navController.navigate(Route.EditProfile.route)
                },
                onLogout = {
                    // 로그인 화면으로 이동 (백 스택 초기화)
                    navController.navigate(Route.Login.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
            )
        }
        composable(route = Route.EditProfile.route) {
            EditProfileScreen(
                modifier = modifier,
                onNavigateBack = { navController.popBackStack() },
            )
        }
        composable(route = Route.Login.route) {
            LoginScreen(
                modifier = modifier,
                onNavigateBack = { navController.popBackStack() },
                onLoginSuccess = {
                    // TODO: 로그인 성공 시 홈 화면으로 이동 또는 이전 화면으로 돌아가기
                    navController.popBackStack()
                },
                onNavigateToRegistration = {
                    navController.navigate(Route.Registration.route)
                },
            )
        }
        composable(route = Route.Registration.route) {
            RegistrationScreen(
                modifier = modifier,
                onNavigateBack = { navController.popBackStack() },
                onRegistrationSuccess = {
                    // 회원가입 성공 시 로그인 화면으로 이동
                    navController.navigate(Route.Login.route) {
                        popUpTo(Route.Registration.route) { inclusive = true }
                    }
                },
            )
        }
        composable(route = Route.Splash.route) {
            SplashScreen(
                modifier = modifier,
                onNavigateToHome = {
                    navController.navigate(Route.Home.route) {
                        popUpTo(Route.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Route.Login.route) {
                        popUpTo(Route.Splash.route) { inclusive = true }
                    }
                },
            )
        }
    }
}
