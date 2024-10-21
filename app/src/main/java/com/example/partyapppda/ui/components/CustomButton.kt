package com.example.partyapppda.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    useGradient: Boolean = true,
    backgroundColor: Color = Color(0xFFD90F96),
    textColor: Color = Color.White,
    borderColor: Color? = null,
    borderWidth: Float? = null
) {
    val buttonModifier = modifier
        .fillMaxWidth()
        .height(66.dp)
        .then(
            if (borderColor != null && borderWidth != null) {
                Modifier.border(
                    width = borderWidth.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(25.dp)
                )
            } else Modifier
        )
        .background(
            brush = if (useGradient) {
                Brush.linearGradient(
                    colors = listOf(Color(0xFFD90F96), Color(0xFF5503AE))
                )
            } else {
                SolidColor(backgroundColor)
            },
            shape = RoundedCornerShape(25.dp)
        )
        .clickable(onClick = onClick)

    Box(
        modifier = buttonModifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = textColor,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}
