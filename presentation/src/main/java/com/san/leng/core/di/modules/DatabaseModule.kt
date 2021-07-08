package com.san.leng.core.di.modules

import android.content.Context
import androidx.room.Room
import com.san.data.LEngDatabase
import com.san.leng.core.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context) =
        Room.databaseBuilder(context, LEngDatabase::class.java, DATABASE_NAME)
//            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDispatcher() = Dispatchers.IO

    @Provides
    fun provideDao(database: LEngDatabase) = database.recordsDao

    @Provides
    fun provideWordsDao(database: LEngDatabase) = database.wordsDao
}
