package com.example.partyapppda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.partyapppda.ui.theme.PartyAppPDATheme
import com.example.partyapppda.ui.screens.login.LoginScreen
import com.example.partyapppda.ui.screens.home.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PartyAppPDATheme {
                var isLoggedIn by remember { mutableStateOf(false) }
                if (isLoggedIn) {
                    HomeScreen()
                } else {
                    LoginScreen(onLoginSuccess = { isLoggedIn = true })
                }
            }
        }
    }
}
