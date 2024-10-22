package com.example.partyapppda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.partyapppda.ui.theme.PartyAppPDATheme
import com.example.partyapppda.ui.screens.login.LoginScreen
import com.example.partyapppda.ui.screens.home.HomeScreen
import com.example.partyapppda.ui.screens.ticket.readEventTiketScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PartyAppPDATheme {
                val navController = rememberNavController()
                var isLoggedIn by remember { mutableStateOf(false) }
                if (isLoggedIn) {
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            HomeScreen(navController = navController)
                        }
                        composable("readEventTiketScreen/{eventId}") { backStackEntry ->
                            val eventId = backStackEntry.arguments?.getString("eventId")?.toIntOrNull()
                            if (eventId != null) {
                                readEventTiketScreen(navController = navController, eventId = eventId)
                            }
                        }
                    }
                } else {
                    LoginScreen(onLoginSuccess = { isLoggedIn = true })
                }
            }
        }
    }
}
