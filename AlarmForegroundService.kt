package com.appu.smarttunealarmx.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import android.net.Uri
import kotlin.concurrent.timer
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.fixedRateTimer
import kotlin.math.min

class AlarmForegroundService : Service() {
    private var player: MediaPlayer? = null
    private var fadeTimer: Timer? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channelId = "alarm_service"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan = NotificationChannel(channelId, "Alarm Service", NotificationManager.IMPORTANCE_HIGH)
            val nm = getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(chan)
        }
        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Alarm")
            .setContentText("Alarm is running")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .build()
        startForeground(1, notification)

        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val uriString = prefs.getString("current_ringtone", null)
        try {
            if (uriString != null) {
                val uri = Uri.parse(uriString)
                player = MediaPlayer().apply {
                    setDataSource(this@AlarmForegroundService, uri)
                    isLooping = true
                    prepare()
                    setVolume(0f, 0f)
                    start()
                }
                // Gradually increase volume over 60 seconds
                val duration = 60_000L
                val steps = 60
                val interval = duration / steps
                var volume = 0f
                fadeTimer = fixedRateTimer("fade", false, 0L, interval) {
                    volume += 1f / steps
                    if (volume >= 1f) {
                        player?.setVolume(1f,1f)
                        this.cancel()
                    } else {
                        player?.setVolume(volume, volume)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        fadeTimer?.cancel()
        player?.stop()
        player?.release()
        player = null
        super.onDestroy()
    }
}
