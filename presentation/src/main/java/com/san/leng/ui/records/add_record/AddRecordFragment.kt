package com.san.leng.ui.records.add_record

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.san.leng.R
import com.san.leng.core.extensions.getClickedWord
import com.san.leng.core.extensions.hideKeyboard
import com.san.leng.core.extensions.setHighlight
import com.san.leng.core.platform.BaseFragment
import com.san.leng.databinding.FragmentAddRecordBinding
import kotlinx.android.synthetic.main.fragment_add_record.*
import kotlinx.android.synthetic.main.fragment_add_record.view.*
import java.util.*

class AddRecordFragment : BaseFragment() {

    private val addRecordsViewModel: AddRecordViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentAddRecordBinding

    private var clickedWord: String? = null

//    private lateinit var datePicker: MaterialDatePicker<Long>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_record, container, false)

        binding.apply {
            viewModel = addRecordsViewModel
            lifecycleOwner = this@AddRecordFragment
        }

        registerForContextMenu(binding.recordText)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupView() {

//        datePicker = MaterialDatePicker.Builder.datePicker()
//            .setTitleText("Select date")
//            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
//            .build()
    }

    private fun setupObservers() {

        addRecordsViewModel.apply {

            saveRecordComplete.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let {
                    hideKeyboard()
                    findNavController().navigate(
                        AddRecordFragmentDirections.actionAddRecordFragmentToRecordsFragment()
                    )
                }
            })

            warningMessage.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            })

            wordDefinition.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let { wordDefinition ->
                    Toast.makeText(requireContext(), wordDefinition, Toast.LENGTH_SHORT).show()
                }
            })

            datePickerClicked.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let {

                    val cal = Calendar.getInstance()
                    val y = cal.get(Calendar.YEAR)
                    val m = cal.get(Calendar.MONTH)
                    val d = cal.get(Calendar.DAY_OF_MONTH)

                    val datePickerDialog =
                        DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->

                            addRecordsViewModel.setDate(year, monthOfYear, dayOfMonth)

                        }, y, m, d)

                    datePickerDialog.show()

//                    datePicker.show(parentFragmentManager, datePicker.toString())
                }
            })
        }
    }

    private fun setupClickListeners() {

        binding.recordText.setOnClickListener {

            val editText = binding.recordText
            clickedWord = editText.getClickedWord()

            clickedWord?.let { editText.setHighlight(clickedWord) }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater? = activity?.menuInflater
        inflater?.inflate(R.menu.word_actions_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.translate -> {
                clickedWord?.let { }
                true
            }
            R.id.definition -> {
                clickedWord?.let {
                    addRecordsViewModel.getWordDefinition(clickedWord)
                }
                true
            }
            R.id.more -> {

                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
