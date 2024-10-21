package com.example.partyapppda.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.partyapppda.R
import com.example.partyapppda.ui.components.CustomButton
import com.example.partyapppda.ui.components.CustomInput
import com.example.partyapppda.ui.components.Loader
import com.example.partyapppda.ui.components.MessageComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showMessage by remember { mutableStateOf(false) }
    var messageState by remember { mutableStateOf("info") }
    var messageText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showResetPasswordModal by remember { mutableStateOf(false) }
    var resetUsernameOrEmail by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Loader(visible = isLoading)
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF100126))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {
                // Header with logo
                Image(
                    painter = painterResource(id = R.drawable.partyapp_img),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(260.dp)
                        .align(Alignment.CenterHorizontally)
                )

                // Elements container
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(
                            color = Color(0xFF1C0A36),
                            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                        )
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = "Inicia sesión",
                        color = Color.White,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Divider(
                        color = Color(0xFFFF0080),
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    CustomInput(
                        placeholder = "Escribe tu usuario o correo",
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.padding(top = 50.dp)
                    )

                    CustomInput(
                        placeholder = "Escribe tu contraseña",
                        value = password,
                        onValueChange = { password = it },
                        secureTextEntry = true,
                        modifier = Modifier
                    )

                    CustomButton(
                        title = "Iniciar sesión",
                        onClick = {
                            // Validación de campos vacíos
                            if (email.trim().isEmpty() || password.trim().isEmpty()) {
                                messageState = "error"
                                messageText = "Por favor, completa todos los campos."
                                showMessage = true
                                return@CustomButton
                            }

                            isLoading = true
                            showMessage = false

                            // Simulación de autenticación
                            coroutineScope.launch {
                                // Simulamos una demora
                                delay(2000)
                                isLoading = false
                                onLoginSuccess()
                                email = ""
                                password = ""
                            }
                        },
                        modifier = Modifier
                            .padding(top = 100.dp)
                            .height(66.dp)
                    )
                }
            }

            if (showMessage) {
                MessageComponent(
                    state = messageState,
                    message = messageText,
                    duration = 3000,
                    onClose = { showMessage = false }
                )
            }
        }
    }
}
