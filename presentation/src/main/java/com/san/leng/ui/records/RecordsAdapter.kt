package com.san.leng.ui.records

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.san.domain.entities.RecordEntity
import com.san.leng.databinding.RecordItemBinding

class RecordsAdapter(private val clickListener: RecordListener) : ListAdapter<RecordEntity, RecordsAdapter.RecordViewHolder>(DiffCallback) {

    class RecordViewHolder(private val binding: RecordItemBinding) : RecyclerView.ViewHolder(binding.root) {
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
        holder.bind(clickListener, getItem(position))
    }
}

class RecordListener(val clickListener: (recordId: Long) -> Unit) {
    fun onClick(record: RecordEntity) = clickListener(record.id)
}