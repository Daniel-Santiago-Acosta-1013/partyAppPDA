package com.example.partyapppda.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.IRScan,
        BottomNavItem.CameraScan,
        BottomNavItem.ScansList
    )

    val currentRoute = navController.currentDestination?.route

    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 1.dp, vertical = 8.dp)
            .padding(bottom = 35.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFD90F96), Color(0xFF5503AE))
                    ),
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(top = 26.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val selected = currentRoute == item.route
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                navController.navigate(item.route) {
                                    navController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (selected) {
                            // Ícono seleccionado dentro de un círculo blanco, color negro
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(Color.White, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    tint = Color.Black,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        } else {
                            // Ícono no seleccionado, color blanco y sin círculo
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
