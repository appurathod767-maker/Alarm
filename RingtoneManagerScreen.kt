package com.appu.smarttunealarmx.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.appu.smarttunealarmx.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ListItem

@Composable
fun RingtoneManagerScreen(navController: NavController) {
    val list = remember { mutableStateListOf<Pair<Long, String>>() }
    val context = androidx.compose.ui.platform.LocalContext.current

    // Launcher for SAF pick (audio/*)
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let {
            // Persist permission and save to DB
            context.contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            // Save to Room in coroutine
            CoroutineScope(Dispatchers.IO).launch {
                val db = AppDatabase.getInstance(context)
                db.ringtoneDao().insert(com.appu.smarttunealarmx.data.RingtoneEntity(uri = it.toString(), name = queryName(context, it)))
            }
        }
    }

    LaunchedEffect(Unit) {
        // load existing ringtones
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            val items = db.ringtoneDao().getAll()
            items.forEach { list.add(it.id.toLong() to it.name) }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { launcher.launch(arrayOf("audio/*")) }) { Text("Add Ringtone") }
        LazyColumn {
            items(list) { item ->
                ListItem(
                    headlineText = { Text(item.second) },
                    trailing = {
                        IconButton(onClick = { /* TODO: delete */ }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                )
            }
        }
    }
}

// Helper to query display name
suspend fun queryName(context: Context, uri: Uri): String {
    return withContext(Dispatchers.IO) {
        var name = "Unknown"
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val idx = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
            if (idx >= 0 && cursor.moveToFirst()) {
                name = cursor.getString(idx)
            }
        }
        name
    }
}
