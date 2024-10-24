package com.example.partyapppda.ui.screens.home

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.partyapppda.ui.theme.PartyAppPDATheme
import com.example.partyapppda.ui.components.FeaturedEventCard
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF1c003e))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "selecciona evento",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.W300,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .drawBehind {
                        val strokeWidth = 2.dp.toPx()
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            color = Color(0xFFFF0080),
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
                    .padding(bottom = 10.dp)
            )
            // Eventos de ejemplo
            val events = listOf(
                EventItem(
                    id = 1,
                    name = "Evento 1",
                    description = "Descripción del evento 1",
                    event_start_date = "2023-10-21T18:00:00",
                    card_banner_mobile = null,
                    categoryTags = listOf("Música", "Fiesta")
                ),
                EventItem(
                    id = 2,
                    name = "Evento 2",
                    description = "Descripción del evento 2",
                    event_start_date = "2023-10-22T20:00:00",
                    card_banner_mobile = null,
                    categoryTags = listOf("Festival")
                )
            )

            Column {
                events.forEach { event ->
                    FeaturedEventCard(
                        eventImage = event.card_banner_mobile,
                        eventTime = formatTime(event.event_start_date),
                        eventDate = formatDate(event.event_start_date),
                        eventTitle = event.name,
                        eventSubtitle = event.description,
                        onClick = {
                            navController.navigate("readEventTiketScreen/${event.id}")
                        }
                    )
                }
            }
        }
    }
}

// Funciones auxiliares para formatear fecha y hora
fun formatTime(dateTime: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val date = inputFormat.parse(dateTime)
    return outputFormat.format(date)
}

fun formatDate(dateTime: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date = inputFormat.parse(dateTime)
    return outputFormat.format(date)
}

// Definición de la clase de datos EventItem
data class EventItem(
    val id: Int,
    val name: String,
    val description: String,
    val event_start_date: String,
    val card_banner_mobile: String?,
    val categoryTags: List<String>
)

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PartyAppPDATheme {
        val navController = rememberNavController()
        HomeScreen(navController)
    }
}
