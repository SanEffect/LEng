package com.san.leng.ui.records.edit_record

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.san.leng.R
import com.san.leng.core.extensions.getClickedWord
import com.san.leng.core.extensions.hideKeyboard
import com.san.leng.core.extensions.setHighlight
import com.san.leng.core.platform.BaseFragment
import com.san.leng.databinding.FragmentEditRecordBinding

class EditRecordFragment : BaseFragment() {

    private val editRecordViewModel: EditRecordViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentEditRecordBinding

    private val args: EditRecordFragmentArgs by navArgs()

    private var clickedWord: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_record, container, false)

        args.recordDto.let {
            editRecordViewModel.currentRecord = it.toEntity()
        }

        binding.apply {
            viewModel = editRecordViewModel
            lifecycleOwner = this@EditRecordFragment
        }

        registerForContextMenu(binding.recordText)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {

        editRecordViewModel.apply {

            updateRecordComplete.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let {
                    hideKeyboard()
                    findNavController().navigate(
                        EditRecordFragmentDirections.actionEditRecordFragmentToRecordsFragment()
                    )
                }
            })

            warningMessage.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let { message ->
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            })

            wordDefinition.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let { wordDefinition ->
                    Toast.makeText(requireContext(), wordDefinition, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun setupClickListeners() {

        binding.recordText.setOnClickListener {

            val editText = binding.recordText
            clickedWord = editText.getClickedWord()

            clickedWord?.let {
                editText.setHighlight(clickedWord)
            }
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

                true
            }
            R.id.definition -> {
                clickedWord?.let {
                    editRecordViewModel.getWordDefinition(clickedWord)
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
