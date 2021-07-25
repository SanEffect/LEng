package com.san.leng.core.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.san.leng.core.di.ViewModelKey
import com.san.leng.ui.ViewModelFactory
import com.san.leng.ui.dictionary.DictionaryViewModel
import com.san.leng.ui.records.RecordsViewModel
import com.san.leng.ui.records.addEditRecord.AddEditRecordViewModel
import com.san.leng.ui.records.recordSlidePager.RecordSlidePagerViewModel
import com.san.leng.ui.statistics.StatisticsViewModel
import com.san.leng.ui.useful_info.UsefulInfoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RecordsViewModel::class)
    abstract fun bindRecodsViewModel(recordsViewModel: RecordsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecordSlidePagerViewModel::class)
    abstract fun bindRecodSlidePagerViewModel(recordSlidePagerViewModel: RecordSlidePagerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddEditRecordViewModel::class)
    abstract fun bindAddEditRecordViewModel(addEditRecordViewModel: AddEditRecordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StatisticsViewModel::class)
    abstract fun bindDashboardViewModel(statisticsViewModel: StatisticsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UsefulInfoViewModel::class)
    abstract fun bindUsefulInfoViewModel(usefulInfoViewModel: UsefulInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DictionaryViewModel::class)
    abstract fun bindDictionaryViewModel(dictionaryViewModel: DictionaryViewModel): ViewModel

    // Factory
    @Binds
    abstract fun bindViewModelFactory(vmFactory: ViewModelFactory): ViewModelProvider.Factory
}
