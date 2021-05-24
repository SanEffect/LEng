package com.san.leng.ui.records

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.san.leng.R
import com.san.leng.core.platform.BaseFragment
import com.san.leng.databinding.FragmentRecordsBinding
import timber.log.Timber

class RecordsFragment : BaseFragment() {

    private val recordsViewModel: RecordsViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentRecordsBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_records, container, false)

        //recordsViewModel = ViewModelProviders.of(this, viewModelFactory).get(RecordsViewModel::class.java)
        binding.viewModel = recordsViewModel

        binding.lifecycleOwner = this

        setupViews()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadRecordsList()
    }

    private fun initializeView() {
        binding.recordList.adapter = RecordsAdapter(RecordListener {
            Timber.i("Go to RecordViewFragment")
            // navigation to RecordViewFragment
        })
    }

    private fun loadRecordsList() {
//        showProgress()
        recordsViewModel.loadRecords()
//        when(recordsViewModel.records.value?.isNotEmpty()) {
//            true -> hideProgress()
//        }
    }

    private fun setupViews() {

        binding.addRecord.setOnClickListener {
            this.findNavController().navigate(RecordsFragmentDirections.actionRecordsFragmentToAddRecordFragment())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.remove_records -> recordsViewModel.clearRecords()
            R.id.action_translate -> Toast.makeText(activity, "Text was translated", Toast.LENGTH_LONG).show()
            //else -> Toast.makeText(activity, "Text was translated", Toast.LENGTH_LONG).show()
        }

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.records_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}