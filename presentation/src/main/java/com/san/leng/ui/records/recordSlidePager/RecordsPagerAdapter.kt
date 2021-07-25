package com.san.leng.ui.records.recordSlidePager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.san.domain.entities.RecordEntity
import com.san.leng.R
import com.san.leng.databinding.FragmentViewRecordBinding

class RecordsPagerAdapter : RecyclerView.Adapter<RecordsPagerAdapter.FragmentViewHolder>() {

    private var records: MutableList<RecordEntity> = mutableListOf()

    class FragmentViewHolder(
        val binding: FragmentViewRecordBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(record: RecordEntity) {
            binding.record = record
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return records.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragmentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: FragmentViewRecordBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_view_record, parent, false)
        return FragmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FragmentViewHolder, position: Int) {
        val record = records[position]
        holder.bind(record)
    }

    fun submitRecords(list: List<RecordEntity>?) {
        list?.let {
            records.clear()
            records.addAll(it as Collection<RecordEntity>)
            notifyDataSetChanged()
        }
    }
}
