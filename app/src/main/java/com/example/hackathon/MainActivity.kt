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
import com.example.hackathon.presentation.navigation.AppNavGraph
import com.example.hackathon.presentation.navigation.BottomNavItem
import com.example.hackathon.presentation.route.Route
import com.example.hackathon.ui.theme.HackathonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // super.onCreate() 호출 전에 반드시 실행
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        // 시스템 스플래시를 즉시 닫도록 설정
        splashScreen.setKeepOnScreenCondition { false }

        enableEdgeToEdge()
        setContent {
            HackathonTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val bottomNavItems =
                    listOf(
                        BottomNavItem("홈", Route.Home.route, Icons.Default.Home),
                        BottomNavItem("등록", Route.Create.route, Icons.Default.Add),
                        BottomNavItem("My", Route.My.route, Icons.Default.Person),
                    )

                // 하단 네비게이션 바를 숨겨야 하는 화면들
                val shouldHideBottomBar =
                    currentRoute == Route.Login.route ||
                        currentRoute == Route.Registration.route ||
                        currentRoute == Route.RegistrationSuccess.route ||
                        currentRoute == Route.Onboarding.route ||
                        currentRoute == Route.EditProfile.route ||
                        currentRoute == Route.Detail.route ||
                        currentRoute == Route.Create.route ||
                        currentRoute == Route.Search.route

                Scaffold(
                    bottomBar = {
                        if (!shouldHideBottomBar) {
                            NavigationBar(
                                containerColor = Color.White,
                            ) {
                                bottomNavItems.forEach { item ->
                                    NavigationBarItem(
                                        selected = currentRoute == item.route,
                                        onClick = {
                                            navController.navigate(item.route) {
                                                launchSingleTop = true
                                                restoreState = true
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = item.icon,
                                                contentDescription = item.label,
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = item.label,
                                            )
                                        },
                                    )
                                }
                            }
                        }
                    },
                ) { innerPadding ->
                    AppNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
