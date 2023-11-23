package com.vladpetryshyn.vyao.repositories.room

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vladpetryshyn.vyao.repositories.room.events.Event
import com.vladpetryshyn.vyao.repositories.room.events.EventDao
import com.vladpetryshyn.vyao.repositories.room.notebooks.Notebook
import com.vladpetryshyn.vyao.repositories.room.notebooks.NotebookDao
import com.vladpetryshyn.vyao.repositories.room.tasks.Task
import com.vladpetryshyn.vyao.repositories.room.tasks.TaskDao
import java.time.YearMonth
import java.util.Date
import java.util.concurrent.Executors


@Database(
    entities = [Notebook::class, Task::class, Event::class],
    version = 7,
    exportSchema = true,
    //autoMigrations = arrayOf(AutoMigration(3, 4))
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notebookDao(): NotebookDao
    abstract fun taskDao(): TaskDao
    abstract fun eventDao(): EventDao


    companion object {
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE task ADD COLUMN is_done INTEGER DEFAULT 0 NOT NULL")
            }
        }
        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE task ADD COLUMN scheduled_for INTEGER DEFAULT NULL")
                database.execSQL("ALTER TABLE task ADD COLUMN time_spent INTEGER DEFAULT 0 NOT NULL")
            }
        }

        @Volatile
        private var Instance: AppDatabase? = null


        fun getDatabase(context: Context): AppDatabase {
            var dbCallback: Callback = object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    Executors.newSingleThreadScheduledExecutor().execute {
                        getDatabase(context).notebookDao().insertNotebook(
                            Notebook(
                                title = "To-Do",
                                updated = Date()
                            )
                        )
                    }
                }
            }

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .addMigrations(MIGRATION_1_2)
                    .addMigrations(MIGRATION_2_3)
                    .addCallback(dbCallback)
                    .build()
            }
        }
    }
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun booleanToInt(bool: Boolean?): Int {
        if (bool != null && bool) {
            return 1
        }
        return 0
    }
}
