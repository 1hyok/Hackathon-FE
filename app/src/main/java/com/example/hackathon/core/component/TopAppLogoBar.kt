package com.example.hackathon.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackathon.R
import com.example.hackathon.ui.theme.HackathonTheme

@Composable
fun TopAppLogoBar(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(HackathonTheme.colors.white)
                .padding(start = 20.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = "logo",
            tint = HackathonTheme.colors.primary,
            modifier = modifier.height(52.dp),
        )
    }
}

@Preview
@Composable
private fun TopAppLogoBarPreview() {
    TopAppLogoBar()
}
