package com.san.leng.ui.dictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.san.domain.entities.WordDefinition
import com.san.leng.databinding.DefinitionItemBinding

class DictionaryAdapter: ListAdapter<WordDefinition, DictionaryAdapter.DefinitionViewHolder>(DiffCallback) {

    class DefinitionViewHolder(private val binding: DefinitionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(wordDefinition: WordDefinition) {
            binding.wordDefinition = wordDefinition
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<WordDefinition>() {
        override fun areItemsTheSame(oldItem: WordDefinition, newItem: WordDefinition): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: WordDefinition, newItem: WordDefinition): Boolean {
            return oldItem.definition == newItem.definition
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder {
        return DefinitionViewHolder(DefinitionItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}