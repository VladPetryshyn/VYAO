package com.vladpetryshyn.vyao.repositories.room.notebooks

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.vladpetryshyn.vyao.repositories.room.tasks.Task
import java.util.Date

@Entity(tableName="notebooks")
data class Notebook(
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val updated: Date,

    @ColumnInfo(name = "is_todo_notebook")
    val isTodoNotebook: Boolean = false,
)

data class NotebookWithTasks(
    @Embedded val notebook: Notebook,
    @Relation(parentColumn = "title", entityColumn = "notebookTitle")
    val tasks: List<Task>
)