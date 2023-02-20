package com.alina.dormitorydemo.navigation

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alina.dormitorydemo.R

@Composable
fun BottomMenu(navController: NavController){
    val menuItems = listOf(BottomMenuScreen.News, BottomMenuScreen.QR, BottomMenuScreen.Profile)

    BottomNavigation(contentColor = colorResource(R.color.white)){
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route

        menuItems.forEach{
            BottomNavigationItem(
                label = {Text(text = it.title)},
                icon = {Icon(imageVector = it.icon, contentDescription = it.title)},
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
                alwaysShowLabel = true,
                selected = currentRoute == it.route,
                onClick = {
                    navController.navigate(it.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}