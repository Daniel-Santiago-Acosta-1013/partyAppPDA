package com.example.partyapppda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.partyapppda.ui.theme.PartyAppPDATheme
import com.example.partyapppda.ui.screens.login.LoginScreen
import com.example.partyapppda.ui.screens.IRScanView
import com.example.partyapppda.ui.screens.CameraScanView
import com.example.partyapppda.ui.screens.ScansListView
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
                var isLoggedIn by remember { mutableStateOf(true) }
                if (isLoggedIn) {
                    NavHost(
                        navController = navController,
                        startDestination = "ir_scan"
                    ) {
                        composable("ir_scan") {
                            IRScanView(navController = navController)
                        }
                        composable("camera_scan") {
                            CameraScanView(navController = navController)
                        }
                        composable("scans_list") {
                            ScansListView(navController = navController)
                        }
                    }
                } else {
                    LoginScreen(onLoginSuccess = { isLoggedIn = true })
                }
            }
        }
    }
}
