package com.appu.smarttunealarmx.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.appu.smarttunealarmx.screens.HomeScreen
import com.appu.smarttunealarmx.screens.RingtoneManagerScreen
import com.appu.smarttunealarmx.screens.SettingsScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("ringtones") { RingtoneManagerScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
    }
}
