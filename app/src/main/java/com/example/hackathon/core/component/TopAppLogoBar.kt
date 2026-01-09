package com.example.hackathon.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackathon.R
import com.example.hackathon.ui.theme.HackathonTheme

@Composable
fun TopAppLogoBar(modifier: Modifier = Modifier) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(HackathonTheme.colors.white)
                .padding(start = 20.dp, top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = "logo",
            tint = HackathonTheme.colors.primary,
            modifier = Modifier.width(90.dp),
        )
    }
}

@Preview
@Composable
private fun TopAppLogoBarPreview() {
    TopAppLogoBar()
}
