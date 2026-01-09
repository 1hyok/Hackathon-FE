package com.example.hackathon.presentation.route

sealed class Route(
    val route: String,
) {
    data object Home : Route(route = "home")

    data object Search : Route(route = "search")

    data object Detail : Route(route = "detail/{id}") {
        fun createRoute(id: String) = "detail/$id"
    }

    data object Create : Route(route = "create")

    data object Search : Route(route = "search")

    data object Login : Route(route = "login")

    data object Registration : Route(route = "registration")

    data object RegistrationSuccess : Route(route = "registration_success")

    data object Onboarding : Route(route = "onboarding")

    data object My : Route(route = "my")
}
