package com.appu.smarttunealarmx.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import java.util.Calendar
import androidx.work.Constraints
import androidx.work.NetworkType

object WorkScheduler {
    fun scheduleDailyMidnightChange(context: Context) {
        val now = Calendar.getInstance()
        val next = Calendar.getInstance()
        next.add(Calendar.DATE, 1)
        next.set(Calendar.HOUR_OF_DAY, 0)
        next.set(Calendar.MINUTE, 0)
        next.set(Calendar.SECOND, 0)
        val initialDelay = next.timeInMillis - now.timeInMillis

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val work = PeriodicWorkRequestBuilder<ChangeRingtoneWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_ringtone_change",
            ExistingPeriodicWorkPolicy.UPDATE,
            work
        )
    }
}
