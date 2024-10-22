package com.example.partyapppda.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.partyapppda.ui.components.BottomNavBar
import com.example.partyapppda.ui.components.TicketItemCard

@Composable
fun ScansListView(navController: NavController) {
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
            Text(
                text = "List of Scans",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                items(10) { index ->
                    TicketItemCard(
                        userName = "User $index",
                        ticketType = "Ticket Type $index",
                        entryTime = "Time $index",
                        status = if (index % 2 == 0) "Accedió" else "Error"
                    )
                }
            }
        }
    }
}
