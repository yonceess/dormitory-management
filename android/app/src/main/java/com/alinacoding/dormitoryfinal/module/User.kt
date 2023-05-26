package com.alinacoding.dormitoryfinal.module

data class User(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val dormitory: String,
    val apartment: String,
    val room: Int,
    val email: String
)

