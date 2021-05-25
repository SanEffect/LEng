package com.san.leng.core.di

import android.content.Context
import com.san.leng.core.di.modules.*
import com.san.leng.ui.MainActivity
import com.san.leng.ui.dashboard.DashboardFragment
import com.san.leng.ui.dictionary.DictionaryFragment
import com.san.leng.ui.records.RecordsFragment
import com.san.leng.ui.records.add_record.AddRecordFragment
import com.san.leng.ui.useful_info.UsefulInfoFragment
import com.san.leng.ui.useful_info.UsefulInfoViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DatabaseModule::class,
    RoomModule::class,
    WordsApiModule::class,
    ViewModelModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    // Activities
    fun inject(mainActivity: MainActivity)

    // Fragments
    fun inject(recordsFragment: RecordsFragment)
    fun inject(addRecordFragment: AddRecordFragment)
    fun inject(dashboardFragment: DashboardFragment)
    fun inject(usefulInfoFragment: UsefulInfoFragment)
    fun inject(dictionaryFragment: DictionaryFragment)
}