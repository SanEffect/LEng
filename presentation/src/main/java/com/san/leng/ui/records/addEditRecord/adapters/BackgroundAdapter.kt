package com.san.leng.ui.records.addEditRecord.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.san.leng.databinding.FragmentAddBackgroundItemBinding

class BackgroundAdapter(
    private val clickListener: BgViewClick,
) : RecyclerView.Adapter<BackgroundAdapter.BackgroundViewHolder>() {

    private var backgroundList: MutableList<String> = mutableListOf()

    class BackgroundViewHolder(
        val binding: FragmentAddBackgroundItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bgItem: String) {
            binding.bgItem = bgItem
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return backgroundList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BackgroundViewHolder {
        return BackgroundViewHolder(
            FragmentAddBackgroundItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: BackgroundViewHolder, position: Int) {
        val bgItem = backgroundList[position]
        holder.bind(bgItem)

        val cardView = holder.binding.bgCard

        cardView.setOnClickListener {

            pickItem(it, bgItem)
            clickListener.onClick(bgItem)
        }
    }

    fun submitBackgroundList(bgList: List<String>?) {
        backgroundList.clear()
        backgroundList.addAll(bgList as Collection<String>)
        notifyDataSetChanged()
    }

    fun pickItem(view: View, recordId: String) {

//        val hasItem = selectedRecords.containsKey(recordId)
//        if (hasItem)
//            selectedRecords.remove(recordId)
//        else
//            selectedRecords[recordId] = view

//        setBackgroundColor(view, hasItem)
    }

    private fun setBackgroundColor(view: View, hasItem: Boolean) {
        val color = if (!hasItem) "#9ab6e2" else "#FFFFFF"
        view.setBackgroundColor(Color.parseColor(color))
    }

    interface BgViewClick {
        fun onClick(selectedColor: String)
    }

}
