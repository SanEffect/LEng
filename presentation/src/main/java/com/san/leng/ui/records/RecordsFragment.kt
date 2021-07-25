package com.san.leng.ui.records

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.san.domain.entities.RecordEntity
import com.san.leng.R
import com.san.leng.core.di.scopes.RecordsScope
import com.san.leng.core.extensions.*
import com.san.leng.core.platform.BaseFragment
import com.san.leng.databinding.FragmentRecordsBinding
import com.san.leng.ui.records.RecordsAdapter.RecordViewClick
import com.san.leng.ui.records.RecordsAdapter.RecordsRemoveListener

class RecordsFragment : BaseFragment() {

    private val args: RecordsFragmentArgs by navArgs()

    private val recordsViewModel: RecordsViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentRecordsBinding

    private lateinit var recordsAdapter: RecordsAdapter

    private var filterIsRotated = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_records, container, false)

        binding.viewModel = recordsViewModel

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recordsViewModel.loadRecords()

        setupMainToolbar()
        setupAdapter()
        setupSnackbar()
        setupObservers()
        setupClickListeners()
    }

    private fun showAllRecordsDeleteDialog() {
        requireContext().alert {
            setTitle("Warning")
            setMessage(getString(R.string.confirm_deleting_all_records))
            positiveButton("DELETE") { recordsViewModel.clearRecords() }
            negativeButton("Cancel") { it.dismiss() }
        }
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, recordsViewModel.snackbarText, Snackbar.LENGTH_SHORT)
        arguments?.let {
            recordsViewModel.showResultMessage(args.userMessage)
        }
    }

    private fun setupAdapter() {
        val onClick: RecordViewClick = object : RecordViewClick {
            override fun onClick(recordEntity: RecordEntity) {

                if (recordsViewModel.isSelectableMode) return

                findNavController().navigate(
                    RecordsFragmentDirections.actionRecordsFragmentToRecordSlidePagerFragment(
                        recordEntity.id,
                        recordEntity.backgroundColor,
                        recordsAdapter.getRecordIds()
                    )
                )
            }

            override fun onLongClick(recordEntity: RecordEntity) {
                if (recordsViewModel.isSelectableMode) return

                recordsViewModel.selectableMode.value = true
                setupSelectableToolbar()
            }
        }

        val removeListener: RecordsRemoveListener = object : RecordsRemoveListener {
            override fun onRecordsRemove(recordIds: List<String>) {
                recordsViewModel.deleteRecords(recordIds)
                recordsViewModel.setSelectableMode(false)
                setupMainToolbar()
            }
        }

        recordsAdapter = RecordsAdapter(onClick, removeListener)
        binding.recordList.adapter = recordsAdapter
    }

    private fun setupClickListeners() {

        binding.apply {

            addRecordFab.setOnClickListener {
                findNavController().navigate(
                    RecordsFragmentDirections.actionRecordsFragmentToAddEditRecordFragment(
                        null
                    )
                )
            }
        }
    }

    private fun setupObservers() {

        recordsViewModel.apply {
            selectableMode.observe(viewLifecycleOwner, {
                recordsAdapter.setSelectionMode(it)
            })
        }
    }

    private fun setupMainToolbar() {

        binding.apply {
            toolbar.menu.clear()
            toolbar.inflateMenu(R.menu.records_frag_main_menu)
            toolbar.setNavigationIcon(R.drawable.ic_drawer)

            toolbar.setNavigationOnClickListener {
                activity?.findViewById<DrawerLayout>(R.id.drawer_layout)?.apply {
                    openDrawer(GravityCompat.START)
                }
            }

            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_records_search -> {
                        view?.showToast("Show search input")

                        true
                    }
                    R.id.action_records_filter -> {
                        val filterButton = toolbar.findViewById<View>(R.id.action_records_filter)
//                        filterButton.rotateBy(180f, 500)

                        filterIsRotated = !filterIsRotated
                        filterButton.rotation = if (filterIsRotated) 180f else 0f

                        recordsViewModel.switchRecordsOrder()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setupSelectableToolbar() {

        binding.apply {

            toolbar.menu.clear()
            toolbar.inflateMenu(R.menu.records_frag_remove_menu)

            toolbar.setNavigationIcon(R.drawable.ic_back_button)

            toolbar.setNavigationOnClickListener {
                recordsViewModel.setSelectableMode(false)
                setupMainToolbar()
            }

            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_records_delete -> {
                        recordsAdapter.removeRecords()
                        true
                    }
                    R.id.action_all_records_delete -> {
                        showAllRecordsDeleteDialog()
                        true
                    }
                    else -> false
                }
            }
        }
    }
}
