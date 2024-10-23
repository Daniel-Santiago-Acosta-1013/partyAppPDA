package com.example.partyapppda.ui.components

import androidx.compose.foundation.background
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
            NavigationBar(
                containerColor = Color.Transparent,
                modifier = Modifier.fillMaxSize()
            ) {
                items.forEach { item ->
                    val selected = currentRoute == item.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
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
                        icon = {
                            if (selected) {
                                // Icono seleccionado dentro de un círculo blanco, color negro
                                Box(
                                    modifier = Modifier
                                        .size(50.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(80.dp)
                                            .background(Color.White, shape = CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = item.title,
                                            tint = Color.Black,
                                            modifier = Modifier.size(55.dp)
                                        )
                                    }
                                }
                            } else {
                                // Icono no seleccionado, color blanco y sin círculo
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    tint = Color.White,
                                    modifier = Modifier.size(55.dp)
                                )
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    }
}
