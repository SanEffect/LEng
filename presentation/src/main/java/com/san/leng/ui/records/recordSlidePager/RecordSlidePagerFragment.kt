package com.san.leng.ui.records.recordSlidePager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.san.leng.R
import com.san.leng.core.platform.BaseFragment
import com.san.leng.databinding.FragmentRecordSlidePagerBinding
import kotlinx.android.synthetic.main.fragment_record_slide_pager.*

class RecordSlidePagerFragment : BaseFragment() {

    private val args: RecordSlidePagerFragmentArgs by navArgs()

    private val recordSlidePagerViewModel: RecordSlidePagerViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentRecordSlidePagerBinding

    private lateinit var adapter: RecordsPagerAdapter

    private lateinit var viewPager: ViewPager2

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        DataBindingUtil.inflate<FragmentRecordSlidePagerBinding>(
            inflater,
            R.layout.fragment_record_slide_pager,
            container,
            false
        ).apply {
            binding = this
            viewModel = recordSlidePagerViewModel
            lifecycleOwner = this@RecordSlidePagerFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupObservers()
        setupClickListenters()
    }

    private fun setupClickListenters() {

        binding.apply {
            recordEditButton.setOnClickListener {
                findNavController().navigate(
                    RecordSlidePagerFragmentDirections.actionRecordSlidePagerFragmentToAddEditRecordFragment(
                        args.recordId
                    )
                )
            }
        }

    }

    private fun setupAdapter() {
        viewPager = record_pager

        adapter = RecordsPagerAdapter()
        viewPager.adapter = adapter

/*        args.let {
            adapter.addRecords(it.recordList)
            adapter.addRecord(it.recordId)
        }*/
//        adapter.submitRecords(listOf(RecordEntity("Rec1"), RecordEntity("Rec2"), RecordEntity("Rec3")))
    }

    private fun setupObservers() {

        recordSlidePagerViewModel.apply {

            navigateBack.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let {
                    findNavController().navigateUp()
                }
            })

            records.observe(viewLifecycleOwner, {
                adapter.submitRecords(it)
            })
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }


}
