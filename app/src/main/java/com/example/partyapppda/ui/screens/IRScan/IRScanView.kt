package com.example.partyapppda.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.partyapppda.ui.components.BottomNavBar
import com.rscja.barcode.BarcodeDecoder
import com.rscja.barcode.BarcodeFactory
import com.rscja.deviceapi.entity.BarcodeEntity
import android.app.Activity
import android.util.Log
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun IRScanView(navController: NavController) {
    val context = LocalContext.current
    val activity = context as? Activity

    var scannedData by rememberSaveable { mutableStateOf<String?>(null) }
    var barcodeDecoder by remember { mutableStateOf<BarcodeDecoder?>(null) }
    var isDecoderInitialized by remember { mutableStateOf(false) }

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
                        } else {
                            scannedData = "Error al decodificar"
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
            barcodeDecoder?.close()
        }
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        modifier = Modifier.background(Color(0xFF1c003e))
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = "IR Scan View",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { barcodeDecoder?.startScan() },
                enabled = isDecoderInitialized
            ) {
                Text(text = "Iniciar Escaneo")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { barcodeDecoder?.stopScan() },
                enabled = isDecoderInitialized
            ) {
                Text(text = "Detener Escaneo")
            }
        }

        if (scannedData != null) {
            AlertDialog(
                onDismissRequest = {
                    scannedData = null
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
                        }
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}
