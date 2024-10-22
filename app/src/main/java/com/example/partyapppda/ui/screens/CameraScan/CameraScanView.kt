package com.example.partyapppda.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.partyapppda.ui.components.BottomNavBar

@Composable
fun CameraScanView(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        modifier = Modifier.background(Color(0xFF1c003e))
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(20.dp)
        ) {
            // Your camera scanning UI goes here
            Text(
                text = "Camera Scan View",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}
