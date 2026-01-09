package com.example.hackathon.presentation.route

sealed class Route(
    val route: String,
) {
    data object Home : Route(route = "home")

    data object Detail : Route("detail") {
        fun createRoute(id: String) = "$route/$id"
    }

    data object Create : Route(route = "create")

    data object Search : Route(route = "search")

    data object Login : Route(route = "login")

    data object Registration : Route(route = "registration")

    data object RegistrationSuccess : Route(route = "registration_success")

    data object Onboarding : Route(route = "onboarding")

    data object My : Route(route = "my")

    data object EditProfile : Route(route = "edit_profile")
}
