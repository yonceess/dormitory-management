package com.alinacoding.dormitoryfinal

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject
import javax.inject.Singleton

private const val KEY_IS_DARK_THEME = "is_dark_theme"

@Singleton
class ThemeManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("ThemePreferences", Context.MODE_PRIVATE)
    private val _isDarkTheme = mutableStateOf(sharedPreferences.getBoolean(KEY_IS_DARK_THEME, false))
    val isDarkTheme = _isDarkTheme

    fun setDarkTheme(isDark: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_DARK_THEME, isDark).apply()
        _isDarkTheme.value = isDark
    }
}
