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
import com.example.hackathon.presentation.screen.LoginScreen
import com.example.hackathon.presentation.screen.MyScreen
import com.example.hackathon.presentation.screen.OnboardingScreen
import com.example.hackathon.presentation.screen.RegistrationScreen
import com.example.hackathon.presentation.screen.RegistrationSuccessScreen
import com.example.hackathon.presentation.screen.SearchScreen
import com.example.hackathon.presentation.screen.home.screen.HomeScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Route.Onboarding.route,
    ) {
        composable(route = Route.Home.route) {
            HomeScreen(
                modifier = modifier,
                navController = navController,
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
        composable(route = Route.Login.route) {
            LoginScreen(
                modifier = modifier,
                onNavigateBack = { navController.popBackStack() },
                onLoginSuccess = {
                    // 로그인 성공 시 홈 화면으로 이동
                    navController.navigate(Route.Home.route) {
                        popUpTo(Route.Onboarding.route) { inclusive = true }
                        launchSingleTop = true
                    }
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
                    // 회원가입 성공 시 가입완료 화면으로 이동
                    navController.navigate(Route.RegistrationSuccess.route) {
                        popUpTo(Route.Registration.route) { inclusive = true }
                    }
                },
            )
        }
        composable(route = Route.RegistrationSuccess.route) {
            RegistrationSuccessScreen(
                modifier = modifier,
                onNavigateToLogin = {
                    navController.navigate(Route.Login.route) {
                        popUpTo(Route.RegistrationSuccess.route) { inclusive = true }
                    }
                },
            )
        }
        composable(route = Route.Onboarding.route) {
            OnboardingScreen(
                modifier = modifier,
                onNavigateToLogin = {
                    navController.navigate(Route.Login.route) {
                        popUpTo(Route.Onboarding.route) { inclusive = true }
                    }
                },
            )
        }
        composable(route = Route.My.route) {
            MyScreen(
                modifier = modifier,
                onCombinationClick = { id ->
                    navController.navigate(Route.Detail.createRoute(id))
                },
                onLogout = {
                    navController.navigate(Route.Login.route) {
                        popUpTo(Route.My.route) { inclusive = true }
                    }
                },
                onChangeNickname = {
                    // TODO: 닉네임 변경 화면으로 이동
                },
            )
        }
    }
}
