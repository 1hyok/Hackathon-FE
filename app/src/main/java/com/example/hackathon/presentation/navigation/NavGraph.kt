package com.example.hackathon.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.hackathon.presentation.route.Route
import com.example.hackathon.presentation.screen.auth.LoginScreen
import com.example.hackathon.presentation.screen.auth.RegistrationScreen
import com.example.hackathon.presentation.screen.auth.RegistrationSuccessScreen
import com.example.hackathon.presentation.screen.combination.CreateCombinationScreen
import com.example.hackathon.presentation.screen.combination.DetailScreen
import com.example.hackathon.presentation.screen.home.HomeScreen
import com.example.hackathon.presentation.screen.home.SearchScreen
import com.example.hackathon.presentation.screen.onboarding.OnboardingScreen
import com.example.hackathon.presentation.screen.profile.EditProfileScreen
import com.example.hackathon.presentation.screen.profile.MyScreen
import com.example.hackathon.presentation.viewmodel.MyPageViewModel

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
            route = Route.Detail.route + "/{id}",
            arguments =
                listOf(
                    navArgument("id") {
                        type = NavType.StringType
                    },
                ),
        ) { backStackEntry ->
            val idString = backStackEntry.arguments?.getString("id")

            val recipeId = idString?.toLongOrNull() ?: -1L

            DetailScreen(
                recipeId = recipeId,
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
                    navController.navigate(Route.EditProfile.route)
                },
            )
        }
        composable(route = Route.EditProfile.route) {
            val myScreenViewModel = hiltViewModel<MyPageViewModel>()
            EditProfileScreen(
                modifier = modifier,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onProfileUpdated = {
                    // 프로필 저장 후 MyScreen의 프로필 새로고침
                    myScreenViewModel.loadProfile()
                },
            )
        }
    }
}
