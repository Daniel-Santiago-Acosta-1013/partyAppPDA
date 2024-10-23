package com.example.partyapppda.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showMessage by rememberSaveable { mutableStateOf(false) }
    var messageState by rememberSaveable { mutableStateOf("info") }
    var messageText by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var showResetPasswordModal by rememberSaveable { mutableStateOf(false) }
    var resetUsernameOrEmail by rememberSaveable { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Loader(visible = isLoading)
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF100126))
        ) {
            // Agregar scroll vertical
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
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
                        .fillMaxHeight()
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
                        style = MaterialTheme.typography.headlineMedium,
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
                            .padding(top = 50.dp, bottom = 150.dp)
                            .height(66.dp)
                            .fillMaxWidth()
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
