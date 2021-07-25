package com.san.leng.ui.records.addEditRecord.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.san.leng.databinding.FragmentAddFontItemBinding

class FontAdapter(
    private val clickListener: FontViewClick,
) : RecyclerView.Adapter<FontAdapter.FontViewHolder>() {

    private var fontList: MutableList<String> = mutableListOf()

    class FontViewHolder(
        val binding: FragmentAddFontItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(fontName: String) {
            binding.fontName = fontName
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return fontList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FontViewHolder {
        return FontViewHolder(
            FragmentAddFontItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: FontViewHolder, position: Int) {
        val fontItem = fontList[position]
        holder.bind(fontItem)

        val cardView = holder.binding.fontCard

        cardView.setOnClickListener {

            pickItem(it, fontItem)
            clickListener.onClick(fontItem)
        }
    }

    fun submitFontList(bgList: List<String>?) {
        fontList.clear()
        fontList.addAll(bgList as Collection<String>)
        notifyDataSetChanged()
    }

    fun pickItem(view: View, fontItem: String) {

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

    interface FontViewClick {
        fun onClick(selectedFont: String)
    }
}
