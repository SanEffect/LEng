package com.san.leng.ui.records

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.san.domain.entities.RecordEntity
import com.san.leng.R
import com.san.leng.core.platform.BaseFragment
import com.san.leng.databinding.FragmentRecordsBinding
import com.san.leng.ui.records.RecordsAdapter.RecordViewClick
import timber.log.Timber

class RecordsFragment : BaseFragment() {

    private val recordsViewModel: RecordsViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentRecordsBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_records, container, false)

        //recordsViewModel = ViewModelProviders.of(this, viewModelFactory).get(RecordsViewModel::class.java)
        binding.viewModel = recordsViewModel

        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadRecordsList()
        initializeView()
    }

    private fun initializeView() {

        val onClick: RecordViewClick = object : RecordViewClick {
            override fun onClick(recordEntity: RecordEntity) {
                findNavController().navigate(
                    RecordsFragmentDirections.actionRecordsFragmentToEditRecordFragment(recordEntity.toDto())
                )
            }
        }

        binding.recordList.adapter = RecordsAdapter(
            RecordContextMenuListener(
                editClickListener = { },
                removeClickListener = { recordId -> recordsViewModel.removeRecord(recordId)}
            ),
            onClick
        )

        val fab = activity?.findViewById<FloatingActionButton>(R.id.add_record_fab)
        fab?.setOnClickListener {
            this.findNavController().navigate(
                RecordsFragmentDirections.actionRecordsFragmentToAddRecordFragment()
            )
        }
    }

    private fun loadRecordsList() {
//        showProgress()
        recordsViewModel.loadRecords()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
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
