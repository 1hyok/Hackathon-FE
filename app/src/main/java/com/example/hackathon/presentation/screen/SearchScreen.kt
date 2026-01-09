package com.example.hackathon.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackathon.R
import com.example.hackathon.presentation.viewmodel.SearchViewModel
import com.example.hackathon.ui.theme.Gray700
import com.example.hackathon.ui.theme.Primary
import kotlinx.coroutines.delay

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onCombinationClick: (String) -> Unit = {},
) {
    var query by rememberSaveable { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 6.dp,
                            clip = false,
                        )
                        .background(HackathonTheme.colors.white)
                        .padding(top = 52.dp, bottom = 20.dp),
            ) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_logo_rec),
                        contentDescription = "back",
                        modifier = Modifier.size(50.dp),
                    )

                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        modifier =
                            Modifier
                                .heightIn(min = 30.dp)
                                .weight(1f)
                                .clip(RoundedCornerShape(30.dp))
                                .border(
                                    width = 1.5.dp,
                                    color = HackathonTheme.colors.primary,
                                    shape = RoundedCornerShape(30.dp),
                                ),
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_search),
                                contentDescription = "search",
                                tint = Color(0xFF8B91A1),
                                modifier =
                                    Modifier.clickable {
                                        viewModel.onSearch(query)
                                    },
                            )
                        },
                        singleLine = true,
                        textStyle = HackathonTheme.typography.Body_medium,
                        colors =
                            TextFieldDefaults.colors(
                                focusedContainerColor = HackathonTheme.colors.white,
                                unfocusedContainerColor = HackathonTheme.colors.white,
                                disabledContainerColor = HackathonTheme.colors.white,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = HackathonTheme.colors.black,
                            ),
                    )
                }
            }
        },
    ) { innerPadding ->
        if (!uiState.hasSearched) {
            // 검색 전 안내 화면
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "쩝쩝박사님들의 레시피를 검색해보세요",
                    style = HackathonTheme.typography.Body_medium,
                    color = HackathonTheme.colors.gray700,
                )
            }
        } else {
            // 검색 후에는 무조건 CombinationList
            CombinationList(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                results = uiState.results,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen()
}
