package com.appu.smarttunealarmx.screens

import androidx.compose.material3.Text
import androidx.compose.material3.Switch
import androidx.compose.material3.Row
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height

@Composable
fun SettingsScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        TopAppBar(title = { Text("Settings") })
        Spacer(modifier = Modifier.height(12.dp))
        Text("Smart Wake-up: Enabled")
        Row { Text("Spotify integration"); Switch(checked = false, onCheckedChange = {}) }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* TODO: logout spotify */ }) { Text("Connect Spotify") }
    }
}
