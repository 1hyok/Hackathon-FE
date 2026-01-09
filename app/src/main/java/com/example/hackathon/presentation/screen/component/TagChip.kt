package com.example.hackathon.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackathon.ui.theme.Primary

@Composable
fun TagChip(
    text: String,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .background(Primary, RoundedCornerShape(15.dp))
                .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
        )
        FilledTonalIconButton(
            onClick = onRemove,
            modifier =
                Modifier
                    .background(Color.White.copy(alpha = 0.2f), shape = CircleShape)
                    .height(24.dp),
            colors = IconButtonDefaults.filledTonalIconButtonColors(containerColor = Color.Transparent),
            content = {
                Text(
                    text = "X",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TagChipPreview() {
    TagChip(
        text = "#서브웨이",
        onRemove = {},
    )
}
