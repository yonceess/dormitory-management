package com.alinacoding.dormitoryfinal.composables


import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alinacoding.dormitoryfinal.composables.destinations.*
import com.google.zxing.integration.android.IntentIntegrator
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun CustomBottomBar(
    navigator: DestinationsNavigator,
    selectedIndex: MutableState<Int>,
    context: Context
) {
//    val listItems = listOf(
//        BottomNavItem("Home", Icons.Filled.Home, ArticleFeedScreenDestination.route),
//        BottomNavItem("Profile", Icons.Filled.Person, ProfileScreenDestination.route),
//        BottomNavItem("Forms", Icons.Filled.Report, UserItemsScreenDestination.route)
//    )

    var navigateToPostQRScreen by remember { mutableStateOf(false) }

    val qrScanLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val contents = IntentIntegrator.parseActivityResult(result.resultCode, result.data)?.contents
            if (contents != null) {
                // Here you can handle the result of the scan. For example, if the contents equal a certain string, navigate to a new page
                if(contents == "fesgsgsrgsrgsfbsfbsfgt") {
                    Log.d("QRCode", "QR code matches, navigating to empty page")
                    navigateToPostQRScreen = true
                } else {
                    Log.d("QRCode", "QR code does not match: $contents")
                }
            } else {
                Log.d("QRCode", "No QR code contents found")
            }
        } else {
            Log.d("QRCode", "QR code scan was not successful")
        }
    }

    LaunchedEffect(navigateToPostQRScreen) {
        if (navigateToPostQRScreen) {
            navigator.navigate(PostQRScreenDestination.route)
            navigateToPostQRScreen = false
        }
    }

    BottomAppBar(
        actions = {
            IconButton(onClick = { navigator.navigate(ArticleFeedScreenDestination.route) }) {
                Icon(Icons.Filled.Home, contentDescription = "Article Feed"
                    ,modifier = Modifier.size(90.dp))
            }

            IconButton(onClick = { navigator.navigate(ProfileScreenDestination.route) }) {
                Icon(Icons.Filled.Person, contentDescription = "User Profile"
                    ,modifier = Modifier.size(90.dp))
            }

            IconButton(onClick = { navigator.navigate(UserItemsScreenDestination.route) }) {
                Icon(Icons.Filled.Report, contentDescription = "Saved items"
                    ,modifier = Modifier.size(90.dp))
            }

            IconButton(onClick = { navigator.navigate(CreateAbsenceScreenDestination.route) }) {
                Icon(Icons.Filled.BugReport, contentDescription = "Absence form"
                    ,modifier = Modifier.size(90.dp))
            }

            IconButton(onClick = { navigator.navigate(CreateItemScreenDestination.route) }) {
                Icon(Icons.Filled.BugReport, contentDescription = "Item malfunction form"
                    ,modifier = Modifier.size(90.dp))
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                        val integrator = IntentIntegrator(context as Activity)
                        integrator.setOrientationLocked(false)
                        integrator.setBeepEnabled(false)
                        integrator.setBarcodeImageEnabled(true)
                        integrator.setPrompt("Scan a QR code")
                        qrScanLauncher.launch(integrator.createScanIntent())
                          },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.QrCodeScanner, "QR Scanner")
            }
        },
        containerColor = Color.White,
        contentColor = MaterialTheme.colorScheme.secondary,
        tonalElevation = 8.dp
    )
}


//    Card(
//        elevation = 0.dp,
//        shape = RoundedCornerShape(20.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//            .height(64.dp)
//    ) {
//        BottomNavigation(backgroundColor = Color.White) {
//            listItems.forEachIndexed { index, item ->
//                val isSelected = selectedIndex.value == index
//                BottomNavigationItem(
//                    icon = { TabIcons(item.icon, isSelected) },
//                    selected = isSelected,
//                    onClick = {
//                        selectedIndex.value = index
//                        navigator.navigate(item.route)
//                    },
//                    alwaysShowLabel = false
//                )
//            }
//        }
//    }
//}

//@Composable
//fun TabIcons(icon: ImageVector, isTintColor: Boolean) {
//    Icon(
//        imageVector = icon,
//        contentDescription = null,
//        tint = if (isTintColor) Color(0xFF1a1f36) else Color.Unspecified
//    )
//}
//
//
//data class BottomNavItem(val name: String, val icon: ImageVector, val route: String)
