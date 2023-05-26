package com.alinacoding.dormitoryfinal.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavOptionsBuilder
import com.alinacoding.dormitoryfinal.ThemeManager
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DormAppBar(
    themeManager: ThemeManager,
    navigator: DestinationsNavigator) {

            TopAppBar(
                colors = topAppBarColors(containerColor = Color.White),
                title = {
                    Text(
                        "Aitu Dormitory",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val newTheme = !themeManager.isDarkTheme.value
                        themeManager.setDarkTheme(newTheme)
                    }) {
                        Icon(
                            imageVector = if (themeManager.isDarkTheme.value) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                            contentDescription = "Toggle theme"
                        )
                    }
                }

            )
        }

//