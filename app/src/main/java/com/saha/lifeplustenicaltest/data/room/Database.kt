package com.saha.lifeplustenicaltest.data.room

import android.content.Context
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.saha.lifeplustenicaltest.data.model.User
import java.util.concurrent.Executors

@androidx.room.Database(
    entities = [User::class],
    version = 1,
    exportSchema = false,

    )
abstract class Database : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: Database? = null
        private val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS)
        fun getDatabase(context: Context): Database? {
            if (INSTANCE == null) {
                synchronized(Database::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = databaseBuilder(
                            context.applicationContext, Database::class.java, "dear_database"
                        ).addCallback(sRoomDatabaseCallback).fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        private val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                databaseWriteExecutor.execute {
                    val dao: DatabaseDao = INSTANCE!!.databaseDao()
                }
            }
        }
    }
}