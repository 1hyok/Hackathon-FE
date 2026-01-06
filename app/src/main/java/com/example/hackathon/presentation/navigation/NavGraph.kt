package com.example.hackathon.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hackathon.presentation.route.Route
import com.example.hackathon.presentation.screen.HomeScreen
import com.example.hackathon.presentation.screen.DiaryScreen
import com.example.hackathon.presentation.screen.MyScreen

@Composable
fun KuitNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
    ){
        composable(route = Route.Home.route) {
            HomeScreen(modifier = modifier)
        }
        composable(route = Route.Diary.route) {
            DiaryScreen(modifier = modifier)
        }
        composable(route = Route.My.route) {
            MyScreen(modifier = modifier)
        }
    }
}

