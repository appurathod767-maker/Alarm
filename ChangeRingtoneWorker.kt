package com.appu.smarttunealarmx.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.appu.smarttunealarmx.data.AppDatabase
import kotlin.random.Random

class ChangeRingtoneWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val db = AppDatabase.getInstance(applicationContext)
        val list = runCatching { db.ringtoneDao().getAll() }.getOrNull() ?: emptyList()
        if (list.isNotEmpty()) {
            val pick = list[Random.nextInt(list.size)]
            val prefs = applicationContext.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            prefs.edit().putString("current_ringtone", pick.uri).apply()
        }
        return Result.success()
    }
}
