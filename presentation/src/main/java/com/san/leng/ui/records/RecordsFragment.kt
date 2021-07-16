package com.san.leng.ui.records

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.san.domain.entities.RecordEntity
import com.san.leng.R
import com.san.leng.core.extensions.alert
import com.san.leng.core.extensions.negativeButton
import com.san.leng.core.extensions.positiveButton
import com.san.leng.core.extensions.setupSnackbar
import com.san.leng.core.platform.BaseFragment
import com.san.leng.databinding.FragmentRecordsBinding
import com.san.leng.ui.records.RecordsAdapter.RecordViewClick
import com.san.leng.ui.records.RecordsAdapter.RecordsRemoveListener

class RecordsFragment : BaseFragment() {

    private val args: RecordsFragmentArgs by navArgs()

    private val recordsViewModel: RecordsViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentRecordsBinding

    private lateinit var recordsAdapter: RecordsAdapter

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
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadRecordsList()

        setupAdapter()
        subscribeUi()
        setupSnackbar()
        setupClickListeners()
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
                    RecordsFragmentDirections.actionRecordsFragmentToAddEditRecordFragment(
                        recordEntity.id
                    )
                )
            }

            override fun onLongClick(recordEntity: RecordEntity) {
                if (recordsViewModel.isSelectableMode) return

                recordsViewModel.selectableMode.value = true
            }
        }

        val removeListener: RecordsRemoveListener = object : RecordsRemoveListener {
            override fun onRecordsRemove(recordIds: List<String>) {
                recordsViewModel.deleteRecords(recordIds)
                recordsViewModel.setSelectableMode(false)
            }
        }

        recordsAdapter = RecordsAdapter(onClick, removeListener)
        binding.recordList.adapter = recordsAdapter
    }

    private fun setupClickListeners() {

        binding.removeAllRecordsButton.setOnClickListener {
            requireContext().alert {
                setTitle("Warning")
                setMessage(getString(R.string.confirm_deleting_all_records))
                positiveButton("DELETE") { recordsViewModel.clearRecords() }
                negativeButton("Cancel") { it.dismiss() }
            }
        }

        binding.removeSelectedRecordsButton.setOnClickListener {
            recordsAdapter.removeRecords()
        }

        val fab = activity?.findViewById<FloatingActionButton>(R.id.add_record_fab)
        fab?.setOnClickListener {
            findNavController().navigate(
                RecordsFragmentDirections.actionRecordsFragmentToAddEditRecordFragment(null)
            )
        }
    }

    private fun subscribeUi() {
        recordsViewModel.apply {

            selectableMode.observe(viewLifecycleOwner, {
                recordsAdapter.setSelectionMode(it)
            })
        }
    }

    private fun loadRecordsList() {
//        showProgress()
        recordsViewModel.loadRecords()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.remove_records -> recordsViewModel.clearRecords()
//            R.id.action_translate -> Toast.makeText(activity, "Text was translated", Toast.LENGTH_LONG).show()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.records_editor_menu, menu)
//        inflater.inflate(R.menu.record_item_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

/*    override fun onCreateContextMenu(menu: ContextMenu?, v: View?,
                                     menuInfo: ContextMenu.ContextMenuInfo?) {
        activity?.menuInflater?.inflate(R.menu.menu_yours, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        super.onContextItemSelected(item)
        when (item?.itemId) {
            R.id.action_your -> yourAction(selectedItemId, selectedItemTitle)
                ...
        }
        return true
    }*/

//    override fun onContextMenuClick(view: View, id: Long, title: String) {
//        // Here we accept item id, title from adapter and show context menu.
////        selectedItemId = id
////        selectedItemTitle = title
//        view.showContextMenu()
//    }
}
