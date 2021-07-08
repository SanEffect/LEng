package com.san.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.san.data.dataAccessObjects.RecordsDao
import com.san.data.dataAccessObjects.WordsDao
import com.san.domain.converters.WordConverters
import com.san.domain.entities.RecordEntity
import com.san.domain.entities.WordEntity

@Database(
    entities = [
        RecordEntity::class,
        WordEntity::class,
    ], version = 1, exportSchema = false
)
@TypeConverters(WordConverters::class)
abstract class LEngDatabase : RoomDatabase() {

    abstract val recordsDao: RecordsDao

    abstract val wordsDao: WordsDao
}
