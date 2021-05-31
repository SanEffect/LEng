package com.san.leng.ui.records

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.san.domain.entities.RecordEntity
import com.san.leng.databinding.RecordItemBinding
import timber.log.Timber

class RecordsAdapter(
    private val contextMenuListener: RecordContextMenuListener,
    private val clickListener: RecordListener
    ) : ListAdapter<RecordEntity, RecordsAdapter.RecordViewHolder>(DiffCallback) {

    class RecordViewHolder(val binding: RecordItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: RecordListener, record: RecordEntity) {
            binding.record = record
//            binding.recordDate.text = convertLongToDate(record.creationDate)
            binding.clickListener = clickListener
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(RecordItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val record = getItem(position)
        holder.bind(clickListener, record)

//        holder.binding.recordItemMoreActions.setOnClickListener {
//            contextMenuCallback.onContextMenuClick(holder.binding.recordItemMoreActions, record.id, record.description)
//        }

        holder.binding.recordCard.setOnCreateContextMenuListener { contextMenu, _, _ ->
            contextMenu.add("Edit").setOnMenuItemClickListener {
                contextMenuListener.onEditClick()
                Timber.i("Go to EditFragment")
                true
            }
            contextMenu.add("Remove").setOnMenuItemClickListener {
                contextMenuListener.onRemoveClick()
                Timber.i("Remove record")
                true
            }
        }
    }

    interface ContextMenuCallback {
        fun onContextMenuClick(view: View, id: Long, title: String)
    }

}

class RecordListener(val clickListener: (recordId: RecordEntity) -> Unit) {
    fun onClick(record: RecordEntity) = clickListener(record)
}

class RecordContextMenuListener(
    val editClickListener: () -> Unit,
    val removeClickListener: () -> Unit
) {
    fun onEditClick() = editClickListener()
    fun onRemoveClick() = removeClickListener()
}