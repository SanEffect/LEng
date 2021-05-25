package com.san.leng

import android.app.Application
import com.san.leng.core.di.AppComponent
import com.san.leng.core.di.DaggerAppComponent
import timber.log.Timber


class AndroidApplication : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}