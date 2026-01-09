package com.example.hackathon.presentation.navigation

import androidx.compose.material.icons.filled.Home
import com.example.hackathon.R
import com.example.hackathon.presentation.route.Route

enum class NavTab(
    val route: String,
    val label: String,
    val icon: Int,
) {
    Home(Route.Home.route, label = "홈", icon = R.drawable.ic_home),
    Create(
        route = Route.Create.route,
        label = "작성",
        icon = R.drawable.ic_add,
    ),
    My(
        route = Route.My.route,
        label = "마이",
        icon = R.drawable.ic_my,
    ),
}
