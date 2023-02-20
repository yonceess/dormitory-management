package com.alina.dormitorydemo.model

import com.alina.dormitorydemo.R

data class News(
    val title: String,
    val image:Int = R.drawable.ic_launcher_background,
    val description: String
    )

