package com.example.hackathon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hackathon.domain.repository.AuthRepository
import com.example.hackathon.presentation.navigation.AppNavGraph
import com.example.hackathon.presentation.navigation.BottomNavBar
import com.example.hackathon.presentation.navigation.BottomNavItem
import com.example.hackathon.presentation.navigation.NavTab
import com.example.hackathon.presentation.route.Route
import com.example.hackathon.ui.theme.HackathonTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { false }

        enableEdgeToEdge()

        setContent {
            HackathonTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val currentTab: NavTab? =
                    NavTab.entries.find { it.route == currentRoute }

                // 하단 네비게이션 바를 숨겨야 하는 화면들
                val shouldHideBottomBar =
                    currentRoute == Route.Login.route ||
                            currentRoute == Route.Registration.route ||
                            currentRoute == Route.RegistrationSuccess.route ||
                            currentRoute == Route.Onboarding.route ||
                            currentRoute == Route.EditProfile.route ||
                            currentRoute == Route.Detail.route ||
                            currentRoute == Route.Search.route

                Scaffold(
                    bottomBar = {
                        if (!shouldHideBottomBar) {
                            BottomNavBar(
                                visible = true,
                                tabs = NavTab.entries,
                                currentTab = currentTab,
                                onItemSelected = { tab ->
                                    navController.navigate(tab.route) {
                                        launchSingleTop = true
                                        restoreState = true
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                    }
                                },
                            )
                        }
                    },
                ) { innerPadding ->
                    AppNavGraph(
                        navController = navController,
                        authRepository = authRepository,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

