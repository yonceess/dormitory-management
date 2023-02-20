package com.alina.dormitorydemo.navigation

sealed class Screens(val route:String){
    object Login:Screens("login")
    object News:Screens("news")
    object Profile:Screens("profile")
}