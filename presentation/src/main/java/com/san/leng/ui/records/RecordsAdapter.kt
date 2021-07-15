package com.san.leng.ui.records

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.san.domain.entities.RecordEntity
import com.san.leng.databinding.RecordItemBinding

class RecordsAdapter(
//    private val contextMenuListener: RecordContextMenuListener,
    private val clickListener: RecordViewClick,
) : ListAdapter<RecordEntity, RecordsAdapter.RecordViewHolder>(DiffCallback) {

    private var recordsList: MutableList<RecordEntity> = mutableListOf()

    class RecordViewHolder(
        private val binding: RecordItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: RecordViewClick, record: RecordEntity) {
            binding.record = record

            binding.recordCard.setOnClickListener {
                clickListener.onClick(record)
            }

            binding.recordCard.setOnLongClickListener {
                clickListener.onLongClick(record)
                true
            }

            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<RecordEntity>() {
        override fun areItemsTheSame(oldItem: RecordEntity, newItem: RecordEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: RecordEntity, newItem: RecordEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun getItemCount(): Int {
        return recordsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(RecordItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
//        val record = getItem(position)
        val record = recordsList[position]
        holder.bind(clickListener, record)

/*        holder.binding.recordCard.setOnCreateContextMenuListener { contextMenu, _, _ ->
            contextMenu.add("Edit").setOnMenuItemClickListener {
                contextMenuListener.onEditClick()
                true
            }
            contextMenu.add("Remove").setOnMenuItemClickListener {
                removeRecord(record, position)
                true
            }
        }*/
    }

    fun submitRecordList(records: List<RecordEntity>?) {
        recordsList.clear()
        recordsList.addAll(records as Collection<RecordEntity>)
        notifyDataSetChanged()
    }

/*    private fun removeRecord(record: RecordEntity, position: Int) {
        recordsList.removeAt(position)
        contextMenuListener.onRemoveClick(record.id)
        notifyDataSetChanged()
    }*/

    interface RecordViewClick {
        fun onClick(recordEntity: RecordEntity)
        fun onLongClick(recordEntity: RecordEntity)
    }
}

/*class RecordContextMenuListener(
    val editClickListener: () -> Unit,
    val removeClickListener: (recordId: String) -> Unit
) {
    fun onEditClick() = editClickListener()
    fun onRemoveClick(recordId: String) = removeClickListener(recordId)
}*/
