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

//    @Inject
//    lateinit var viewModelFactory: ViewModelFactory

    private val addRecordsViewModel: AddRecordViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentAddRecordBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AndroidApplication).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val text = Intent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
        Log.i("AddRecordFragment", "$text")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_record, container, false)

        //addRecordsViewModel = ViewModelProviders.of(this, viewModelFactory).get(AddRecordViewModel::class.java)
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

        addRecordsViewModel.res.observe(viewLifecycleOwner, Observer {
            Timber.i(it)
        })

        addRecordsViewModel.records.observe(viewLifecycleOwner, Observer {
            it.forEach {
                Timber.i("${it.title}")
            }

        })

        binding.recordText.setOnClickListener {
            val word = getWord()
            setHighLightedText(binding.recordText, word)
        }

        val checkedListener =
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    actionMode = view?.startActionMode(actionModeCallback, ActionMode.TYPE_FLOATING)
                    actionMode!!.title = "Action Mode"
                } else {
                    if (actionMode != null) {
                        actionMode!!.finish()
                    }
                }
            }

        registerForContextMenu(binding.recordText)

        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        return binding.root
    }

    var actionMode: ActionMode? = null

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

    override fun onStop() {
        super.onStop()
        Timber.i("AddRecordsFragment has stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("AddRecordsFragment has destroyed")
    }

    private fun getWordIndexBySelectionStart(match: MatchResult?, selectionStart: Int) : Int? {
        return if(match?.range?.contains(selectionStart) == false) getWordIndexBySelectionStart(
            match.next(),
            selectionStart
        ) else match?.range?.first
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setHighLightedText(textView: TextView, textToHighlight: String) {
        if (textToHighlight.isEmpty()) return

        val text = textView.text.toString()
        val match = textToHighlight.toRegex().find(text)
        val index: Int = getWordIndexBySelectionStart(match, binding.recordText.selectionStart) ?: return

        binding.recordText.setSelection(index, index + textToHighlight.length)

//        val wordToSpan: Spannable = SpannableString(textView.text)
//        wordToSpan.setSpan(
//            BackgroundColorSpan(Color.argb(100, 255, 255, 0)),
//            ind,
//            ind + textToHighlight.length,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//        textView.setText(wordToSpan, TextView.BufferType.SPANNABLE)

//        view?.startActionMode(actionModeCallback, ActionMode.TYPE_FLOATING)

//        binding.recordText.showContextMenu()

//        activity?.openContextMenu(view)

//        var ofs = 0
//        while(ofs < tvt.length && ofe != -1) {
//            ofe = tvt.indexOf(textToHighlight, ofs)
//            if(ofe == -1) break
//            else {
//                wordToSpan.setSpan(
//                    BackgroundColorSpan(Color.YELLOW),
//                    ofe,
//                    ofe + textToHighlight.length,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//                tv.setText(wordToSpan, TextView.BufferType.SPANNABLE)
//            }
//            ofs++
//        }
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

    private val actionModeCallback = object : ActionMode.Callback {
        // Called when the action mode is created; startActionMode() was called
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Inflate a menu resource providing context menu items
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.action_mode_menu, menu)
            return true
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_test -> {

                    mode.finish() // Action picked, so close the CAB
                    true
                }
                else -> false
            }
        }

        // Called when the user exits the action mode
        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null
        }
    }
}