package com.alinacoding.dormitoryfinal

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alinacoding.dormitoryfinal.composables.NavGraphs
import com.example.compose.DormitoryFinalTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {

    private lateinit var themeManager: ThemeManager

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val themeManager = ThemeManager(this)

        setContent {
            CompositionLocalProvider(LocalThemeManager provides themeManager) {
                DormitoryFinalTheme(useDarkTheme = themeManager.isDarkTheme.value) {
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        DestinationsNavHost(navGraph = NavGraphs.root)
                    }
                }
            }
        }
    }
}



//    @Composable
//    private fun MainContent(loginViewModel: LoginViewModel) {
//        val context = LocalContext.current
//        val toastMessage = loginViewModel.toastMessage.value
//
//        LaunchedEffect(toastMessage) {
//            if (!toastMessage.isNullOrBlank()) {
//                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
//                loginViewModel.clearToastMessage()
//            }
//        }
//
//        DormitoryFinalTheme {
//            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
//                DestinationsNavHost(navGraph = NavGraphs.root)
//            }
//        }
//    }
