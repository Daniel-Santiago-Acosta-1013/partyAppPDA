package com.example.partyapppda.ui.screens.home

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.partyapppda.ui.theme.PartyAppPDATheme

@Composable
fun HomeScreen() {
    Scaffold { innerPadding ->
        Greeting(
            name = "Usuario",
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "¡Bienvenido, $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PartyAppPDATheme {
        HomeScreen()
    }
}
