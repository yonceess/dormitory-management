package com.alina.dormitorydemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.alina.dormitorydemo.navigation.BottomMenu
import com.alina.dormitorydemo.ui.screens.LoginScreen
import com.alina.dormitorydemo.ui.screens.NewsScreen
import com.alina.dormitorydemo.ui.screens.newsList
import com.alina.dormitorydemo.ui.theme.DormitoryDemoTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DormitoryDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomMenu(navController) }
                    ) {
                        //LoginScreen(navController = rememberNavController())
                        //DormApp()
                        NewsScreen()
                    }
                    //ProfileScreen()
                    //NewsScreen()
                    //LoginScreen(navController = rememberNavController())
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DormitoryDemoTheme {
        //LoginScreen(navController = rememberNavController())
        //ProfileScreen()
        //DormApp()
        NewsScreen()
    }
}