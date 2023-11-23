package com.vladpetryshyn.vyao.repositories.room.events

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Transaction
    @Query("SELECT * FROM events WHERE date_key = :t ORDER BY reminder_date ASC")
    fun getByDateKey(t: String): Flow<List<EventWithTask>>

    @Insert
    suspend fun insertEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Update
    suspend fun updateEvent(event: Event)

    @Query("SELECT * FROM events WHERE year_month = :yearMonth")
    fun selectByYearMonth(yearMonth: String): Flow<List<Event>>
}