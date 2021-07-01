package com.san.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.san.data.dataAccessObjects.RecordsDao
import com.san.domain.entities.RecordEntity

@Database(
    entities = [
        RecordEntity::class
//    WordEntity::class,
//    LanguageEntity::class
    ], version = 1, exportSchema = false
)
abstract class RecordsDatabase : RoomDatabase() {

    abstract val recordsDao: RecordsDao

//    abstract val wordsDao: WordsDao
}
