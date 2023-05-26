package com.alinacoding.dormitoryfinal.composables

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavOptionsBuilder
import com.alinacoding.dormitoryfinal.LocalThemeManager
import com.alinacoding.dormitoryfinal.R
import com.alinacoding.dormitoryfinal.composables.destinations.PostQRScreenDestination
import com.alinacoding.dormitoryfinal.composables.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun PostQRScreen(
    navigator: DestinationsNavigator
) {
    val coroutineScope = rememberCoroutineScope()

    val remainingTime = remember { mutableStateOf(30) }

    LaunchedEffect(key1 = true) {
        for (i in 30 downTo 0) {
            remainingTime.value = i

            delay(1000L)

            if (i == 0) {
                navigator.navigate(ProfileScreenDestination.route){
                    popUpTo(PostQRScreenDestination.route){
                        inclusive = true
                    }
                }
                navigator.clearBackStack(ProfileScreenDestination.route)
            }
        }
    }

    val selectedIndex = remember { mutableStateOf(1) }
    val context = LocalContext.current
    val themeManager = LocalThemeManager.current
    Scaffold(
        topBar = { DormAppBar(themeManager, navigator) },
        bottomBar = { CustomBottomBar(navigator, selectedIndex, context) },
//        drawerContent = {
//            val context = LocalContext.current
//            Sidebar(
//                navigator = navigator,
//                currentRoute = PostQRScreenDestination.route,
//                context = context
//            )
//        },
        modifier = Modifier.background(Color(0xFF94C7CB)),
        content = { innerPadding ->

            Box(
                modifier = Modifier.fillMaxSize()
                    .consumeWindowInsets(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.success_icon),
                        contentDescription = "Success Icon",
                        modifier = Modifier.size(170.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "QR verification gone successful",
                        fontSize = 24.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${remainingTime.value}",
                        fontSize = 30.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    )
}


