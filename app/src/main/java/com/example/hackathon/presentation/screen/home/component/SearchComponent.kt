package com.example.hackathon.presentation.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackathon.R
import com.example.hackathon.ui.theme.HackathonTheme

@Composable
fun SearchComponent(modifier: Modifier = Modifier) {
    Spacer(Modifier.padding(top = 30.dp))
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_logo_rec),
            contentDescription = "logo",
            modifier = Modifier.size(width = 35.dp, height = 35.dp),
        )
        Row(
            modifier =
                modifier
                    .size(width = 300.dp, height = 35.dp)
                    .border(
                        color = HackathonTheme.colors.primary,
                        width = 1.5.dp,
                        shape = RoundedCornerShape(30.dp),
                    )
                    .background(
                        HackathonTheme.colors.white,
                        shape = RoundedCornerShape(30.dp),
                    )
                    .padding(start = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "search component",
                tint = Color(0xFF8B91A1),
            )
            Text(
                text = "오늘은 어떤 꿀조합을 찾고 싶으신가요?",
                style = HackathonTheme.typography.Caption_medium,
                color = Color(0xFF8B91A1),
            )
        }
    }
}

@Preview
@Composable
private fun SearchComponentPreview() {
    SearchComponent()
}
