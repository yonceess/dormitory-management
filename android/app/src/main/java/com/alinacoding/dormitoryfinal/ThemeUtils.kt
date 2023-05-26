package com.alinacoding.dormitoryfinal

import androidx.compose.runtime.staticCompositionLocalOf

val LocalThemeManager = staticCompositionLocalOf<ThemeManager> { error("No ThemeManager found!") }
