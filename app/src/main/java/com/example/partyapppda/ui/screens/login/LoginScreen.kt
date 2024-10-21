package com.example.partyapppda.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.example.partyapppda.ui.components.CustomInput
import com.example.partyapppda.ui.components.CustomButton

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        CustomInput(
            placeholder = "Correo electrónico",
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(10.dp))
        CustomInput(
            placeholder = "Contraseña",
            value = password,
            onValueChange = { password = it },
            secureTextEntry = true,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomButton(
            title = "Iniciar Sesión",
            onClick = {
                // Implementa aquí la lógica de autenticación
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    // Aquí podrías validar el correo y la contraseña
                    onLoginSuccess()
                } else {
                    // Mostrar un mensaje de error o manejar campos vacíos
                }
            },
            useGradient = true
        )
    }
}
