package com.appu.smarttunealarmx.screens

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height

@Composable
fun HomeScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        TopAppBar(title = { Text("SmartTune Alarm") })
        Spacer(modifier = Modifier.height(12.dp))
        Text("Welcome to SmartTune Alarm - home screen.")
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { navController.navigate("ringtones") }) { Text("Manage Ringtones") }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("settings") }) { Text("Settings") }
    }
}
