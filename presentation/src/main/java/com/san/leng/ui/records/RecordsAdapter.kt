package com.san.leng.ui.records

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.san.domain.entities.RecordEntity
import com.san.leng.databinding.RecordItemBinding

class RecordsAdapter(
    private val clickListener: RecordViewClick,
    private val removeListener: RecordsRemoveListener
) : ListAdapter<RecordEntity, RecordsAdapter.RecordViewHolder>(DiffCallback) {

    private var recordsList: MutableList<RecordEntity> = mutableListOf()

    private val selectedRecords = HashMap<String, View>()

    private var selectionMode = false

    class RecordViewHolder(
        val binding: RecordItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(record: RecordEntity) {
            binding.record = record
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return recordsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(RecordItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val record = recordsList[position]
        holder.bind(record)

        val cardView = holder.binding.recordCard

        cardView.setOnClickListener {

            if (selectionMode) {
                pickItem(it, record.id)
                return@setOnClickListener
            }

            clickListener.onClick(record)
        }

        cardView.setOnLongClickListener {

            if (!selectionMode) pickItem(it, record.id)

            clickListener.onLongClick(record)
            true
        }
    }

    fun getRecordIds(): Array<String> {
        return recordsList.map { it.id }.toTypedArray()
    }

    fun submitRecordList(records: List<RecordEntity>?) {
        recordsList.clear()
        recordsList.addAll(records as Collection<RecordEntity>)
        notifyDataSetChanged()
    }

    private fun getSelectedRecordIds(): List<String> {
        return selectedRecords.keys.map { it }
    }

    private fun clearSelections() {
        selectedRecords.forEach {
            setBackgroundColor(it.value, true)
        }
        selectedRecords.clear()
    }

    fun pickItem(view: View, recordId: String) {

        val hasItem = selectedRecords.containsKey(recordId)
        if (hasItem)
            selectedRecords.remove(recordId)
        else
            selectedRecords[recordId] = view

        setBackgroundColor(view, hasItem)
    }

    private fun setBackgroundColor(view: View, hasItem: Boolean) {
        val color = if (!hasItem) "#9ab6e2" else "#FFFFFF"
        view.setBackgroundColor(Color.parseColor(color))
    }

    fun setSelectionMode(state: Boolean) {
        selectionMode = state

        if (!selectionMode) clearSelections()
    }

    fun removeRecords() {

        val ids = getSelectedRecordIds()
        val records = recordsList.filter { ids.contains(it.id) }
        recordsList.removeAll(records)
        clearSelections()
        notifyDataSetChanged()

        removeListener.onRecordsRemove(ids)
    }

    interface RecordViewClick {
        fun onClick(recordEntity: RecordEntity)
        fun onLongClick(recordEntity: RecordEntity)
    }

    interface RecordsRemoveListener {
        fun onRecordsRemove(recordIds: List<String>)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<RecordEntity>() {
        override fun areItemsTheSame(oldItem: RecordEntity, newItem: RecordEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: RecordEntity, newItem: RecordEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
