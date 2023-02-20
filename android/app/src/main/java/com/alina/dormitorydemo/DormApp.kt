package com.alina.dormitorydemo

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alina.dormitorydemo.model.MockData
import com.alina.dormitorydemo.navigation.BottomMenuScreen
import com.alina.dormitorydemo.navigation.Screens
import com.alina.dormitorydemo.ui.screens.LoginScreen
import com.alina.dormitorydemo.ui.screens.NewsScreen
import com.alina.dormitorydemo.ui.screens.ProfileScreen

@Composable
fun DormApp(){
    val navController = rememberNavController()
}


@Composable
fun NavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = BottomMenuScreen.News.route
    ){
        composable(route= BottomMenuScreen.QR.route){
            NewsScreen()
        }

        composable(route= BottomMenuScreen.Profile.route){
            ProfileScreen()
        }
    }
}

