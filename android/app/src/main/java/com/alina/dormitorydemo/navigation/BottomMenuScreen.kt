package com.alina.dormitorydemo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomMenuScreen(
    val route: String,
    val icon: ImageVector,
    val title: String
){
    object News: BottomMenuScreen(route = "news", icon = Icons.Outlined.Home, title = "News")
    object QR: BottomMenuScreen(route = "qr", icon = Icons.Outlined.QrCodeScanner, title = "QR")
    object Profile: BottomMenuScreen(route = "profile", icon = Icons.Outlined.Person, title = "Profile")
}

