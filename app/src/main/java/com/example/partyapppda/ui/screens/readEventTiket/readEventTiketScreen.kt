package com.example.partyapppda.ui.screens.ticket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.partyapppda.ui.components.TicketItemCard
import androidx.compose.ui.draw.clip

@Composable
fun readEventTiketScreen(navController: NavController, eventId: Int) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF1c003e))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Banner del evento
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.Gray),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "ElectroVibes 2024",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Una Noche de Ritmos Electrónicos",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Text(
                        text = "9:00 PM / COL, Septiembre 9",
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .background(Color(0xFF5503AE), shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Últimos tickets leídos
            Text(
                text = "Últimos tickets leídos",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            // Lista de tickets leídos
            LazyColumn {
                items(3) {
                    TicketItemCard(
                        userName = "Santiago Ariza",
                        ticketType = "Entrada VIP",
                        entryTime = "10:00 PM",
                        status = if (it == 0) "Accedió" else "Error"
                    )
                }
            }
        }
    }
}
