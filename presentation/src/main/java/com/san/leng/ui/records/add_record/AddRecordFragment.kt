package com.san.leng.ui.records.add_record

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.san.leng.AndroidApplication
import com.san.leng.R
import com.san.leng.databinding.FragmentAddRecordBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import androidx.lifecycle.Observer
import com.san.leng.core.extensions.hideKeyboard
import com.san.leng.core.platform.BaseFragment



class AddRecordFragment : BaseFragment() {

    private val addRecordsViewModel: AddRecordViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentAddRecordBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_record, container, false)

        binding.viewModel = addRecordsViewModel

        addRecordsViewModel.saveRecordComplete.observe(viewLifecycleOwner, Observer {
            if (it == true) {

                hideKeyboard()
                Timber.i("Back to RecordFragment. Title: ${addRecordsViewModel.title.value.toString()}")

                this.findNavController().navigate(
                    AddRecordFragmentDirections.actionAddRecordFragmentToRecordsFragment()
                )
                addRecordsViewModel.doneNavigation()
            }
        })

        addRecordsViewModel.message.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        })

        binding.recordText.setOnClickListener {
            val word = getWord()
            setHighLightedText(binding.recordText, word)
        }

        registerForContextMenu(binding.recordText)

        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getWord() : String {
        val startSelection = binding.recordText.selectionStart

        var length = 0
        var selectedWord = ""
        val wordsArray = binding.recordText.text.toString().split(" ".toRegex()).toTypedArray()

        for (currentWord in wordsArray) {
            length += currentWord.length + 1
            if (length > startSelection) {
                selectedWord = currentWord
                break
            }
        }

        return selectedWord
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_save_record -> addRecordsViewModel.saveRecord()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.addrecord_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun getWordIndexBySelectionStart(match: MatchResult?, selectionStart: Int) : Int? {
        return if(match?.range?.contains(selectionStart) == false) getWordIndexBySelectionStart(
            match.next(),
            selectionStart
        ) else match?.range?.first
    }

    private fun setHighLightedText(textView: TextView, textToHighlight: String) {
        if (textToHighlight.isEmpty()) return

        val text = textView.text.toString()
        val match = textToHighlight.toRegex().find(text)
        val index: Int = getWordIndexBySelectionStart(match, binding.recordText.selectionStart) ?: return

        binding.recordText.setSelection(index, index + textToHighlight.length)
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
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {

            else -> super.onContextItemSelected(item)
        }
    }
}