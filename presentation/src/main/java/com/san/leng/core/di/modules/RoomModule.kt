package com.san.leng.core.di.modules

import com.san.data.repositories.RecordsRepository
import com.san.data.sources.local.IRecordsLocalDataSource
import com.san.data.sources.local.IWordsLocalDataSource
import com.san.data.sources.local.RecordsLocalDataSource
import com.san.data.sources.local.WordsLocalDataSource
import com.san.domain.repositories.IRecordsRepository
import dagger.Binds
import dagger.Module

@Module(includes = [DatabaseModule::class])
abstract class RoomModule {

    @Binds
    abstract fun provideRecordsLocalDataSource(dataSource: RecordsLocalDataSource): IRecordsLocalDataSource

    @Binds
    abstract fun provideWordsLocalDataSource(wordsLocalDataSource: WordsLocalDataSource): IWordsLocalDataSource

    @Binds
    abstract fun provideRecordsRepository(repository: RecordsRepository): IRecordsRepository
}
