package com.example.partyapppda.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val title: String, val route: String, val icon: ImageVector) {
    object IRScan : BottomNavItem("IR Scan", "ir_scan", Icons.Filled.QrCodeScanner)
    object CameraScan : BottomNavItem("Camera Scan", "camera_scan", Icons.Filled.CameraAlt)
    object ScansList : BottomNavItem("Scans List", "scans_list", Icons.Filled.List)
}
