package com.san.leng.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

//class ViewModelFactory @Inject constructor(
//    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>) : T {
//        val viewModelProvider = viewModels[modelClass]
//            ?: throw IllegalArgumentException("model class $modelClass not found")
//        return viewModelProvider.get() as T
//    }
//}

@Singleton
@Suppress("UNCHECKED_CAST")
class ViewModelFactory
@Inject constructor(private val creators: Map<Class<out ViewModel>,
        @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?:
        creators.asIterable().firstOrNull { modelClass.isAssignableFrom(it.key) }?.value ?:
        throw IllegalArgumentException("Unknown ViewModel class $modelClass")

        return try { creator.get() as T }
        catch (e: Exception) { throw RuntimeException(e) }
    }
}

//class ViewModelFactory(val app: Application) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return DashboardViewModel(app) as T
//        }
//        throw IllegalArgumentException("Unable to construct viewmodel")
//    }
//}