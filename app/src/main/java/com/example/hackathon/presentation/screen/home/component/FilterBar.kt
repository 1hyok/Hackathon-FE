package com.example.hackathon.presentation.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hackathon.ui.theme.HackathonTheme

@Composable
fun FilterBar(
    modifier: Modifier = Modifier,
    filters: List<String>,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
) {
    Column {
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 0.5.dp,
                        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                        clip = false,
                        ambientColor = Color.Black.copy(alpha = 0.6f),
                        spotColor = Color.Black.copy(alpha = 0.9f),
                    )
                    .background(
                        color = HackathonTheme.colors.primary,
                        shape =
                            RoundedCornerShape(
                                topStart = 15.dp,
                                topEnd = 15.dp,
                            ),
                    )
                    .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "꿀조합",
                style = HackathonTheme.typography.Head2_bold,
                color = HackathonTheme.colors.white,
            )
        }

        LazyRow(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(HackathonTheme.colors.white)
                    .padding(vertical = 12.dp),
            horizontalArrangement =
                Arrangement.spacedBy(
                    space = 20.dp,
                    alignment = Alignment.CenterHorizontally,
                ),
        ) {
            items(filters) { filter ->
                val isSelected = filter == selectedFilter

                Text(
                    text = filter,
                    style = HackathonTheme.typography.Body_semibold,
                    color =
                        if (isSelected) {
                            HackathonTheme.colors.primary
                        } else {
                            HackathonTheme.colors.gray700
                        },
                    modifier =
                        Modifier
                            .clickable {
                                onFilterSelected(filter)
                            }
                            .padding(horizontal = 8.dp),
                )
            }
        }
    }
}
