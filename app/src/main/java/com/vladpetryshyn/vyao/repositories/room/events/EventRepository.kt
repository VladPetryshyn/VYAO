package com.vladpetryshyn.vyao.repositories.room.events

import androidx.work.Data
import com.vladpetryshyn.vyao.repositories.workmanager.WorkManagerNotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    private val eventDao: EventDao,
    private val workManagerNotificationRepository: WorkManagerNotificationRepository
) {

    fun getByDate(date: String): Flow<List<EventWithTask>> {
        return eventDao.getByDateKey(date)
    }

    suspend fun insertEvent(event: Event, data: Data, delay: Long, workName: String) {
        workManagerNotificationRepository.scheduleNotification(
            delay,
            data,
            workName
        )
        eventDao.insertEvent(event)
    }


    suspend fun deleteEvent(event: Event){
        workManagerNotificationRepository.cancelNotification(event.workerId)
        eventDao.deleteEvent(event)
    }

    suspend fun updateEvent(event: Event, data: Data, delay: Long) {
        workManagerNotificationRepository.cancelNotification(event.workerId)
        eventDao.updateEvent(event)
        workManagerNotificationRepository.scheduleNotification(
            delay,
            data,
            event.workerId
        )
    }

    fun selectByYearMonth(yearMonth: String): Flow<List<Event>> {
        return eventDao.selectByYearMonth(yearMonth)
    }
}