package com.alinacoding.dormitoryfinal.composables

import android.Manifest
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alinacoding.dormitoryfinal.composables.destinations.*
import com.google.zxing.integration.android.IntentIntegrator
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

//
//
//@Composable
//fun Sidebar(
//    navigator: DestinationsNavigator,
//    currentRoute: String,
//    context: Context
//    //onLogoutClick: () -> Unit
//) {
//
//    var navigateToComplaintFormScreen by remember { mutableStateOf(false) }
//
//    val qrScanLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            val contents = IntentIntegrator.parseActivityResult(result.resultCode, result.data)?.contents
//            if (contents != null) {
//                // Here you can handle the result of the scan. For example, if the contents equal a certain string, navigate to a new page
//                if(contents == "fesgsgsrgsrgsfbsfbsfgt") {
//                    Log.d("QRCode", "QR code matches, navigating to empty page")
//                    navigateToComplaintFormScreen = true
//                } else {
//                    Log.d("QRCode", "QR code does not match: $contents")
//                }
//            } else {
//                Log.d("QRCode", "No QR code contents found")
//            }
//        } else {
//            Log.d("QRCode", "QR code scan was not successful")
//        }
//    }
//
//    // This will be called whenever navigateToComplaintFormScreen changes to true
//    LaunchedEffect(navigateToComplaintFormScreen) {
//        if (navigateToComplaintFormScreen) {
//            navigator.navigate(CreateItemScreenDestination.route)
//            navigateToComplaintFormScreen = false
//        }
//    }
//
//    var showDialog by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxHeight()
//            .background(MaterialTheme.colorScheme.primaryContainer)
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "AITU Dormitory",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.White, // Changed color to White for better contrast
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//        SidebarButton(
//            text = "Feed",
//            icon = Icons.Default.Home, // Added icon
//            isSelected = currentRoute == "feed",
//            onClick = { navigator.navigate(ArticleFeedScreenDestination.route) }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        SidebarButton(
//            text = "Profile",
//            icon = Icons.Default.Person, // Added icon
//            isSelected = currentRoute == "profile",
//            onClick = { navigator.navigate(ProfileScreenDestination.route) }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        SidebarButton(
//            text = "Complaint Forms",
//            icon = Icons.Default.Description,
//            isSelected = currentRoute == "forms",
//            onClick = { navigator.navigate(CreateItemScreenDestination.route) }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//        SidebarButton(
//            text = "Absence Form",
//            icon = Icons.Default.Description,
//            isSelected = currentRoute == "forms",
//            onClick = { navigator.navigate(CreateAbsenceScreenDestination.route) }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//        SidebarButton(
//            text = "Sent Forms",
//            icon = Icons.Default.Description,
//            isSelected = currentRoute == "forms",
//            onClick = { navigator.navigate(UserItemsScreenDestination.route) }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//        SidebarButton(
//            text = "QR Scanner",
//            icon = Icons.Default.QrCodeScanner, // Added icon
//            isSelected = currentRoute == "scanner",
//            onClick = {
//                val integrator = IntentIntegrator(context as Activity)
//                integrator.setOrientationLocked(false)
//                integrator.setBeepEnabled(false)
//                integrator.setBarcodeImageEnabled(true)
//                integrator.setPrompt("Scan a QR code")
//                qrScanLauncher.launch(integrator.createScanIntent())
//            }
//        )
//
//
//        Spacer(modifier = Modifier.height(16.dp))
//        SidebarButton(
//            text = "Log out",
//            icon = Icons.Filled.Logout,
//            isSelected = false,
//            onClick = { TODO() }
//        )
//    }
//}

@Composable
fun SidebarButton(
    text: String,
    icon: ImageVector,
    //isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
            .background(Color.Transparent)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = text,
            color = Color.Black
        )
    }
}