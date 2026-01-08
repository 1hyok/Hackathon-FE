package com.example.hackathon.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.hackathon.presentation.route.Route

enum class NavTab(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    Home(Route.Home.route, label = "홈", icon = Icons.Default.Home),
    Search(
        route = Route.Search.route,
        label = "검색",
        icon = Icons.Default.Search,
    ),
    Create(
        route = Route.Create.route,
        label = "작성",
        icon = Icons.Default.Add,
    ),
    My(
        route = Route.My.route,
        label = "마이",
        icon = Icons.Default.Person,
    ),
}
