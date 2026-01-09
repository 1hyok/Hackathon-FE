package com.example.hackathon.core.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hackathon.ui.theme.HackathonTheme

@Composable
fun IngredientRow(
    name: String,
    amount: String,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            style = HackathonTheme.typography.Body_medium,
            color = HackathonTheme.colors.black,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = amount,
            style = HackathonTheme.typography.Body_medium,
            color = HackathonTheme.colors.gray700,
        )
    }
}
