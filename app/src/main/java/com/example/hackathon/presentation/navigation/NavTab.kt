package com.example.hackathon.presentation.navigation

import androidx.annotation.StringRes
import com.example.hackathon.R
import com.example.hackathon.core.designsystem.R as DesignSystemR
import com.example.hackathon.presentation.route.Route

enum class NavTab(val route: String, @param:StringRes val label: Int, val icon: Int) {
    Home(Route.Home.route, label = R.string.nav_home, icon = DesignSystemR.drawable.ic_home),
    Create(
        route = Route.Create.route,
        label = R.string.nav_create,
        icon = DesignSystemR.drawable.ic_add
    ),
    My(
        route = Route.My.route,
        label = R.string.nav_my,
        icon = DesignSystemR.drawable.ic_my
    )
}
