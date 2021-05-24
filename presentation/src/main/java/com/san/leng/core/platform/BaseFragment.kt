package com.san.leng.core.platform

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.san.leng.AndroidApplication
import com.san.leng.core.di.AppComponent
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

//    abstract fun layoutId(): Int

    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }

//    @Inject
//    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
//            inflater.inflate(layoutId(), container, false)

    open fun onBackPressed() {}

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    internal fun showProgress() = progressStatus(View.VISIBLE)

    internal fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
            with(activity) { if (this is BaseActivity) this.progress.visibility = viewStatus }

//    internal fun notify(@StringRes message: Int) =
//            Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()
//
//    internal fun notifyWithAction(@StringRes message: Int, @StringRes actionText: Int, action: () -> Any) {
//        val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_INDEFINITE)
//        snackBar.setAction(actionText) { _ -> action.invoke() }
//        snackBar.setActionTextColor(ContextCompat.getColor(appContext, color.colorTextPrimary))
//        snackBar.show()
//    }
}