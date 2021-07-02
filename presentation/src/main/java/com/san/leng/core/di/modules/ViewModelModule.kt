package com.san.leng.core.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.san.leng.core.di.ViewModelKey
import com.san.leng.ui.ViewModelFactory
import com.san.leng.ui.dashboard.DashboardViewModel
import com.san.leng.ui.dictionary.DictionaryViewModel
import com.san.leng.ui.records.RecordsViewModel
import com.san.leng.ui.records.add_record.AddRecordViewModel
import com.san.leng.ui.records.edit_record.EditRecordViewModel
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
    @ViewModelKey(AddRecordViewModel::class)
    abstract fun bindAddRecodViewModel(addRecordViewModel: AddRecordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditRecordViewModel::class)
    abstract fun bindEditRecodViewModel(editRecordViewModel: EditRecordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(dashboardViewModel: DashboardViewModel): ViewModel

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
