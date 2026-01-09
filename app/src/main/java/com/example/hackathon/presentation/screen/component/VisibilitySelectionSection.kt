package com.example.hackathon.presentation.screen.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackathon.ui.theme.Gray700
import com.example.hackathon.ui.theme.Primary

@Composable
fun VisibilitySelectionSection(
    isPublic: Boolean,
    onPublicChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "공개 여부",
            style = MaterialTheme.typography.bodyMedium,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // 전체 공개 버튼
            if (isPublic) {
                Button(
                    onClick = { onPublicChange(true) },
                    modifier = Modifier.weight(1f),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Primary,
                            contentColor = Color.White,
                        ),
                ) {
                    Text("전체 공개")
                }
            } else {
                OutlinedButton(
                    onClick = { onPublicChange(true) },
                    modifier = Modifier.weight(1f),
                    colors =
                        ButtonDefaults.outlinedButtonColors(
                            contentColor = Primary,
                        ),
                    border = BorderStroke(1.dp, Gray700),
                ) {
                    Text("전체 공개")
                }
            }
            // 나만 보기 버튼
            if (!isPublic) {
                Button(
                    onClick = { onPublicChange(false) },
                    modifier = Modifier.weight(1f),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Primary,
                            contentColor = Color.White,
                        ),
                ) {
                    Text("나만 보기")
                }
            } else {
                OutlinedButton(
                    onClick = { onPublicChange(false) },
                    modifier = Modifier.weight(1f),
                    colors =
                        ButtonDefaults.outlinedButtonColors(
                            contentColor = Primary,
                        ),
                    border = BorderStroke(1.dp, Gray700),
                ) {
                    Text("나만 보기")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VisibilitySelectionSectionPreview() {
    VisibilitySelectionSection(
        isPublic = true,
        onPublicChange = {},
    )
}
