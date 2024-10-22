package com.example.partyapppda.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.partyapppda.ui.components.BottomNavBar
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

@Composable
fun CameraScanView(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var scannedData by rememberSaveable { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )

    if (!hasCameraPermission) {
        LaunchedEffect(Unit) {
            launcher.launch(Manifest.permission.CAMERA)
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
        ) {
            if (hasCameraPermission) {
                CameraPreview(
                    onBarcodeScanned = { barcode ->
                        scannedData = barcode
                    }
                )
            } else {
                Text(
                    text = "Se requiere permiso de cámara para escanear códigos QR.",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (scannedData != null) {
                AlertDialog(
                    onDismissRequest = {
                        scannedData = null
                    },
                    title = {
                        Text(text = "Código QR Escaneado")
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
}

@Composable
fun CameraPreview(
    onBarcodeScanned: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { androidx.camera.view.PreviewView(context) }

    val scanner = remember { BarcodeScanning.getClient() }

    var executor: ExecutorService? = remember { Executors.newSingleThreadExecutor() }

    DisposableEffect(Unit) {
        onDispose {
            executor?.shutdown()
            executor = null
        }
    }

    LaunchedEffect(cameraProviderFuture) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        // Definir el analizador como una clase separada
        imageAnalysis.setAnalyzer(executor!!, BarcodeAnalyzer(scanner, onBarcodeScanned))

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalysis
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    AndroidView(
        factory = { previewView },
        modifier = Modifier.fillMaxSize()
    )
}

// Definir el analizador en una clase separada y anotar el método analyze
private class BarcodeAnalyzer(
    private val scanner: com.google.mlkit.vision.barcode.BarcodeScanner,
    private val onBarcodeScanned: (String) -> Unit
) : ImageAnalysis.Analyzer {

    @androidx.camera.core.ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        val barcode = barcodes[0]
                        val rawValue = barcode.rawValue
                        if (rawValue != null) {
                            onBarcodeScanned(rawValue)
                        }
                    }
                }
                .addOnFailureListener {
                    // Manejar errores si es necesario
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
}
