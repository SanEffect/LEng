package com.san.leng.ui.useful_info

import android.content.Context
import com.san.leng.R
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.san.leng.core.platform.BaseFragment
import com.san.leng.databinding.FragmentUsefulInfoBinding
import timber.log.Timber

class UsefulInfoFragment: BaseFragment() {

    private val usefulInfoViewModel: UsefulInfoViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentUsefulInfoBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_useful_info, container, false)

        binding.viewModel = usefulInfoViewModel

        setupViews()
        return binding.root
    }

    private fun setupViews() {

        binding.searchInfo.setOnEditorActionListener { _, actionId, _ ->
            true
        }

        binding.searchInfo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateListFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updateListFromInput() {

    }
}