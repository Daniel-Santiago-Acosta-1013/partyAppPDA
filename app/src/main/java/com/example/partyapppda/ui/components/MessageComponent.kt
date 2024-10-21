package com.example.partyapppda.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun MessageComponent(
    state: String,
    message: String,
    duration: Long,
    onClose: () -> Unit
) {
    var visible by remember { mutableStateOf(true) }

    val gradientColors = when (state) {
        "error" -> listOf(Color(0xFFD70926), Color(0x6B6C0513))
        "alert" -> listOf(Color(0xFFEE6C00), Color(0x6B773600))
        "info" -> listOf(Color(0xFFFAB600), Color(0x6B7D5B00))
        "approved" -> listOf(Color(0xFF09D95C), Color(0x6B056D2E))
        "news" -> listOf(Color(0xFF3A5DA7), Color(0x6B1D2F54))
        else -> listOf(Color(0xFF3A5DA7), Color(0x6B1D2F54))
    }

    val icon = when (state) {
        "error" -> Icons.Default.Error
        "alert" -> Icons.Default.Warning
        "info" -> Icons.Default.Info
        "approved" -> Icons.Default.CheckCircle
        "news" -> Icons.Default.Help
        else -> Icons.Default.Info
    }

    LaunchedEffect(Unit) {
        delay(duration)
        visible = false
        onClose()
    }

    AnimatedVisibility(visible = visible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80000000))
                .padding(top = 25.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(
                        brush = Brush.horizontalGradient(gradientColors),
                        shape = RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 10.dp,
                            bottomStart = 25.dp,
                            bottomEnd = 40.dp
                        )
                    )
                    .padding(vertical = 15.dp, horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = message,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            visible = false
                            onClose()
                        }
                )
            }
        }
    }
}
