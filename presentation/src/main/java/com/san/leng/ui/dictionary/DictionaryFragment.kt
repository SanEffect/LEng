package com.san.leng.ui.dictionary

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.san.leng.R
import com.san.leng.core.extensions.hideKeyboard
import com.san.leng.core.platform.BaseFragment
import com.san.leng.databinding.FragmentDictionaryBinding
import timber.log.Timber

class DictionaryFragment : BaseFragment() {

    private val dictionaryViewModel: DictionaryViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentDictionaryBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dictionary, container, false)

        binding.viewModel = dictionaryViewModel

        binding.lifecycleOwner = this

        setupViews()
        return binding.root
    }

    private fun setupViews() {

        binding.foundedWordDesc.adapter = DictionaryAdapter()

        binding.searchWord.setOnEditorActionListener { _, actionId, _ ->
            updateWordFromInput()
            true
        }

        binding.searchWord.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateWordFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updateWordFromInput() {
        binding.searchWord.text.trim().let {
            hideKeyboard()
            dictionaryViewModel.getWordDefinition(it.toString())
        }
    }

}