package com.example.hackathon.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackathon.core.util.noRippleClickable
import com.example.hackathon.ui.theme.HackathonTheme
import kotlin.enums.EnumEntries

@Composable
fun BottomNavBar(
    visible: Boolean,
    tabs: EnumEntries<NavTab>,
    currentTab: NavTab?,
    onItemSelected: (NavTab) -> Unit,
) {
    if (!visible) return

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 0.5.dp,
                    clip = true,
                    ambientColor = Color.Black.copy(alpha = 0.6f),
                    spotColor = Color.Black.copy(alpha = 0.9f),
                )
                .background(
                    color = Color.White,
                ),
    ) {
        Column {
            Row(
                modifier =
                    Modifier
                        .padding(horizontal = 30.dp, vertical = 10.dp)
                        .navigationBarsPadding()
                        .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                tabs.forEach { tab ->
                    val isSelected = tab == currentTab

                    Column(
                        modifier =
                            Modifier
                                .weight(1f)
                                .noRippleClickable {
                                    onItemSelected(tab)
                                },
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            painter = painterResource(id = tab.icon),
                            contentDescription = tab.label,
                            tint =
                                if (isSelected) {
                                    HackathonTheme.colors.black
                                } else {
                                    Color(0xFF8B91A1)
                                },
                        )
                        Text(
                            text = tab.label,
                            fontSize = 12.sp,
                            color =
                                if (isSelected) {
                                    HackathonTheme.colors.black
                                } else {
                                    Color(0xFF939DA9)
                                },
                        )
                    }
                }
            }
        }
    }
}
