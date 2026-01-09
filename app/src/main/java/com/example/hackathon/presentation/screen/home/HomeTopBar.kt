package com.example.hackathon.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hackathon.presentation.component.SearchComponent
import com.example.hackathon.ui.theme.HackathonTheme

@Composable
fun HomeTopBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    HomeTopBar(
        onSearchClick = {
            navController.navigate("search")
        },
        modifier = modifier,
    )
}

@Composable
fun HomeTopBar(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 6.dp,
                    clip = false,
                )
                .background(HackathonTheme.colors.white)
                .padding(top = 30.dp, bottom = 20.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 25.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            SearchComponent(
                onSearchClick = onSearchClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeTopBarPreview() {
    HackathonTheme {
        HomeTopBar(
            onSearchClick = {},
        )
    }
}
