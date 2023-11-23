package com.vladpetryshyn.vyao.repositories.room.events

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.vladpetryshyn.vyao.repositories.room.tasks.Task
import java.util.Date

@Entity(
    tableName = "events",
    foreignKeys = [
        ForeignKey(
            entity = Task::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("task_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Event(
    @ColumnInfo(name = "worker_id")
    @PrimaryKey(autoGenerate = false)
    val workerId: String,
    @ColumnInfo(name = "task_id")
    val taskId: Int,
    @ColumnInfo("reminder_date")
    val reminderDate: Date,
    @ColumnInfo("date_key")
    val dateKey: String,
    @ColumnInfo("year_month")
    val yearMonth: String
)

fun generateEventWorkerName(
    timestamp: Long,
    taskId: Int,
): String {
    return "=$timestamp::$taskId="
}

data class EventWithTask(
    @Embedded
    val event: Event,
    @Relation(
        parentColumn = "task_id",
        entityColumn = "id"
    )
    val task: Task,
)