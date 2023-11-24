package com.vladpetryshyn.vyao.workers

import android.app.Notification.DEFAULT_ALL
import android.app.NotificationManager
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.getActivity
import android.app.job.JobInfo.PRIORITY_MAX
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.vladpetryshyn.vyao.MainActivity
import com.vladpetryshyn.vyao.NOTIFICATION_CHANNEL
import com.vladpetryshyn.vyao.R

class NotifyWork(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        val id = inputData.getInt(TASK_ID, 0)
        sendNotification(id)

        return Result.success()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun sendNotification(id: Int) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NOTIFICATION_ID, id)

        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // val bitmap = applicationContext.vectorToBitmap(R.drawable.ic_schedule_black_24dp)
        val titleNotification = inputData.getString(TASK_TITLE)
        val subtitleNotification = inputData.getString(TASK_DESCRIPTION)
        val pendingIntent = getActivity(applicationContext, 0, intent, FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
            //.setLargeIcon(bitmap)
            .setSmallIcon(R.drawable.schedule_24px)
            .setContentTitle(titleNotification).setContentText(subtitleNotification)
            .setDefaults(DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notification.priority = PRIORITY_MAX
        }
        notificationManager.notify(id, notification.build())
    }


    companion object {
        const val NOTIFICATION_ID = "vyao_notification_id"
    }
}

const val TASK_TITLE = "TASK_TITLE"
const val TASK_DESCRIPTION = "TASK_DESCRIPTION"
const val TASK_ID = "TASK_ID"
