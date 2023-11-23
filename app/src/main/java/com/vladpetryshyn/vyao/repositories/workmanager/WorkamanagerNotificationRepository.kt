package com.vladpetryshyn.vyao.repositories.workmanager

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.vladpetryshyn.vyao.workers.NotifyWork
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkManagerNotificationRepository
@Inject constructor(
    private val workManager: WorkManager
) {
    fun scheduleNotification(
        delay: Long,
        data: Data,
        name: String
    ) {
        val notificationWork = OneTimeWorkRequestBuilder<NotifyWork>()
             .setInitialDelay(delay, TimeUnit.MILLISECONDS)
             .setInputData(data)

        workManager.enqueueUniqueWork(
            name,
            ExistingWorkPolicy.APPEND,
            notificationWork.build()
        )
    }

    fun cancelNotification(workManagerName: String) {
        workManager.cancelUniqueWork(workManagerName)
    }
}