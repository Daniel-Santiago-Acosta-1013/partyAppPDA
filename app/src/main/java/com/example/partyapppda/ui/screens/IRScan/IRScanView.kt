package com.example.partyapppda.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.partyapppda.ui.components.BottomNavBar
import com.airbnb.lottie.compose.*
import com.rscja.barcode.BarcodeDecoder
import com.rscja.barcode.BarcodeFactory
import com.rscja.deviceapi.entity.BarcodeEntity
import android.app.Activity
import android.util.Log
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.partyapppda.R

@Composable
fun IRScanView(navController: NavController) {
    val context = LocalContext.current
    val activity = context as? Activity

    var scannedData by rememberSaveable { mutableStateOf<String?>(null) }
    var barcodeDecoder by remember { mutableStateOf<BarcodeDecoder?>(null) }
    var isDecoderInitialized by remember { mutableStateOf(false) }
    var isScanning by remember { mutableStateOf(false) }

    // Inicializar el decodificador de códigos de barras
    LaunchedEffect(activity) {
        if (activity != null && !isDecoderInitialized) {
            try {
                val decoder = BarcodeFactory.getInstance().barcodeDecoder
                decoder.open(activity)

                // Configurar el callback para recibir los datos escaneados
                decoder.setDecodeCallback(object : BarcodeDecoder.DecodeCallback {
                    override fun onDecodeComplete(barcodeEntity: BarcodeEntity) {
                        if (barcodeEntity.resultCode == BarcodeDecoder.DECODE_SUCCESS) {
                            scannedData = barcodeEntity.barcodeData
                            isScanning = false
                        } else {
                            scannedData = "Error al decodificar"
                            isScanning = false
                        }
                    }
                })

                barcodeDecoder = decoder
                isDecoderInitialized = true
            } catch (e: Exception) {
                Log.e("IRScanView", "Error al inicializar el decodificador", e)
            }
        }
    }

    // Cerrar el decodificador al salir de la pantalla
    DisposableEffect(Unit) {
        onDispose {
            barcodeDecoder?.stopScan()
            barcodeDecoder?.close()
        }
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        modifier = Modifier.background(Color(0xFF1c003e))
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF1c003e)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Lottie animation
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.pda_animation))
                val animationState = animateLottieCompositionAsState(
                    composition = composition,
                    isPlaying = isScanning,
                    iterations = LottieConstants.IterateForever
                )

                LottieAnimation(
                    composition = composition,
                    progress = { animationState.progress },
                    modifier = Modifier.size(280.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Escanea con IR la entrada del asistente",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Animated button with breathing effect
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    // Background circles
                    BreathingCircles(isScanning)

                    // Main button
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color(0xFFD90F96), Color(0xFF5503AE))
                                )
                            )
                            .clickable(enabled = isDecoderInitialized) {
                                if (isScanning) {
                                    barcodeDecoder?.stopScan()
                                    isScanning = false
                                } else {
                                    barcodeDecoder?.startScan()
                                    isScanning = true
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "IR",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            if (scannedData != null) {
                AlertDialog(
                    onDismissRequest = {
                        scannedData = null
                        isScanning = false
                    },
                    title = {
                        Text(text = "Datos Escaneados")
                    },
                    text = {
                        Text(text = scannedData ?: "")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                scannedData = null
                                isScanning = false
                            }
                        ) {
                            Text("Aceptar")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BreathingCircles(isScanning: Boolean) {
    if (isScanning) {
        val infiniteTransition = rememberInfiniteTransition(label = "BreathingTransition")

        val scale1 by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 3f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "Scale1"
        )

        val alpha1 by infiniteTransition.animateFloat(
            initialValue = 0.5f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "Alpha1"
        )

        val scale2 by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 3f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 4000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(1000)
            ),
            label = "Scale2"
        )

        val alpha2 by infiniteTransition.animateFloat(
            initialValue = 0.5f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 4000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(1000)
            ),
            label = "Alpha2"
        )

        val scale3 by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 3f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 5000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(2000)
            ),
            label = "Scale3"
        )

        val alpha3 by infiniteTransition.animateFloat(
            initialValue = 0.5f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 5000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(2000)
            ),
            label = "Alpha3"
        )

        Box(
            modifier = Modifier.size(200.dp),
            contentAlignment = Alignment.Center
        ) {
            // Circle 1
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .scale(scale1)
                    .clip(CircleShape)
                    .background(
                        Color(0xFFD90F96).copy(alpha = alpha1)
                    )
            )
            // Circle 2
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .scale(scale2)
                    .clip(CircleShape)
                    .background(
                        Color(0xFFD90F96).copy(alpha = alpha2)
                    )
            )
            // Circle 3
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .scale(scale3)
                    .clip(CircleShape)
                    .background(
                        Color(0xFFD90F96).copy(alpha = alpha3)
                    )
            )
        }
    }
}
