package com.san.leng.core.di.modules

import android.content.Context
import androidx.room.Room
import com.san.data.RecordsDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

private const val DATABASE_NAME = "records_database"

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context) =
        Room.databaseBuilder(context, RecordsDatabase::class.java, DATABASE_NAME).build()

    @Provides
    fun provideDispatcher() = Dispatchers.IO

    @Provides
    fun provideDao(database: RecordsDatabase) = database.recordsDao

//    @Provides
//    fun provideWordsDao(database: RecordsDatabase) = database.wordsDao
}