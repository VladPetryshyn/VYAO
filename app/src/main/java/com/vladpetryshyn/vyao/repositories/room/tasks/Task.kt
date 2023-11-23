package com.vladpetryshyn.vyao.repositories.room.tasks

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.vladpetryshyn.vyao.repositories.room.events.Event
import com.vladpetryshyn.vyao.repositories.room.notebooks.Notebook
import java.util.Date

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Notebook::class,
            parentColumns = arrayOf("title"),
            childColumns = arrayOf("notebook_title"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    @ColumnInfo(name = "notebook_title")
    val notebookTitle: String,
    val description: String,
    @ColumnInfo(name = "modification_date")
    val updated: Date,

    @ColumnInfo(
        name = "is_done",
    )
    val isDone: Boolean = false,

    @ColumnInfo(name = "scheduled_for")
    val scheduledFor: Date? = null,

    @ColumnInfo(name = "time_spent")
    val timeSpent: Long = 0,
)

data class TaskWithEvents(
    @Embedded
    val task: Task,
    @Relation(
        parentColumn = "id",
        entityColumn = "task_id"
    )
    val events: List<Event>
)