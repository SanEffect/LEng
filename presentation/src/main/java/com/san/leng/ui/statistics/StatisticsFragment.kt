package com.san.leng.ui.statistics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.san.leng.R
import com.san.leng.core.platform.BaseFragment
import com.san.leng.databinding.FragmentStatisticsBinding

class StatisticsFragment : BaseFragment() {

    private val statisticsViewModel: StatisticsViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentStatisticsBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false)

        binding.viewModel = statisticsViewModel

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadStatistics()
    }

    private fun loadStatistics() {
        statisticsViewModel.loadRecordsCount()
        statisticsViewModel.loadWordsCount()
    }
}
