package com.san.leng.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.san.leng.R
import com.san.leng.core.platform.BaseFragment
import com.san.leng.databinding.FragmentDashboardBinding

class DashboardFragment : BaseFragment() {

    private val dashboardViewModel: DashboardViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentDashboardBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)

        binding.viewModel = dashboardViewModel

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadStatistics()
    }

    private fun loadStatistics() {
        dashboardViewModel.loadRecordsCount()
        dashboardViewModel.loadWordsCount()
    }
}