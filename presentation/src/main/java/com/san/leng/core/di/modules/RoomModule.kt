package com.san.leng.core.di.modules

import com.san.data.repositories.RecordsRepository
import com.san.data.sources.local.IRecordsDataSource
import com.san.data.sources.local.IRecordsLocalDataSource
import com.san.domain.repositories.IRecordsRepository
import dagger.Binds
import dagger.Module

@Module(includes = [DatabaseModule::class])
abstract class RoomModule {

    @Binds
    abstract fun provideRecordsLocalDataSource(dataSource: IRecordsLocalDataSource): IRecordsDataSource

    @Binds
    abstract fun provideRecordsRepository(repository: RecordsRepository): IRecordsRepository
}
