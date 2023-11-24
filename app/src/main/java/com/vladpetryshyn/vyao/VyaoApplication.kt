package com.vladpetryshyn.vyao

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color.RED
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.AudioAttributes.USAGE_NOTIFICATION_RINGTONE
import android.media.RingtoneManager.TYPE_NOTIFICATION
import android.media.RingtoneManager.getDefaultUri
import android.os.Build
import androidx.compose.ui.res.stringResource
import dagger.hilt.android.HiltAndroidApp

const val NOTIFICATION_CHANNEL = "vyao_reminders"
@HiltAndroidApp
class VyaoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val remindersChannel = NotificationChannel(
                NOTIFICATION_CHANNEL,
                resources.getString(R.string.reminders_channel_name),
                NotificationManager.IMPORTANCE_HIGH,
            )
             val ringtoneManager = getDefaultUri(TYPE_NOTIFICATION)
             val audioAttributes = AudioAttributes.Builder().setUsage(USAGE_NOTIFICATION_RINGTONE)
                 .setContentType(CONTENT_TYPE_SONIFICATION).build()

             remindersChannel.description = resources.getString(R.string.reminders_channel_description)
             remindersChannel.enableLights(true)
             remindersChannel.lightColor = RED
             remindersChannel.enableVibration(true)
             remindersChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
             remindersChannel.setSound(ringtoneManager, audioAttributes)
             val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
             notificationManager.createNotificationChannel(remindersChannel)
        }
    }
}