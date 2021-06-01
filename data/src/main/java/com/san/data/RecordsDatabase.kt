package com.san.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.san.data.DAOs.RecordsDao
import com.san.data.DAOs.WordsDao
import com.san.domain.entities.LanguageEntity
import com.san.domain.entities.RecordEntity
import com.san.domain.entities.WordEntity

@Database(entities = [
    RecordEntity::class
//    WordEntity::class,
//    LanguageEntity::class
], version = 1, exportSchema = false)
abstract class RecordsDatabase: RoomDatabase() {

    abstract val recordsDao: RecordsDao

//    abstract val wordsDao: WordsDao

    companion object {

        @Volatile
        private lateinit var INSTANCE: RecordsDatabase

        fun getInstance(context: Context) : RecordsDatabase {
            synchronized(RecordsDatabase::class.java) {
                if (!Companion::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            RecordsDatabase::class.java,
                            "records_database")
//                            .fallbackToDestructiveMigration() // TODO: remove it
                            .build()
                }
            }
            return INSTANCE
        }
    }
}