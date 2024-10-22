package com.example.partyapppda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.partyapppda.ui.theme.PartyAppPDATheme
import com.example.partyapppda.ui.screens.login.LoginScreen
import com.example.partyapppda.ui.screens.home.HomeScreen
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
                    HomeScreen(navController = navController)
                } else {
                    LoginScreen(onLoginSuccess = { isLoggedIn = true })
                }
            }
        }
    }
}
