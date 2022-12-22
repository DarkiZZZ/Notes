package ru.msokolov.notesapp.ui.note.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.msokolov.notesapp.data.room.item.ItemEntity
import ru.msokolov.notesapp.databinding.ItemLayoutBinding

class ItemAdapter(private val clickListener: ItemClickListener):
    ListAdapter<ItemEntity, ItemAdapter.ViewHolder>(TaskDiffCallback) {

    companion object TaskDiffCallback : DiffUtil.ItemCallback<ItemEntity>(){
        override fun areItemsTheSame(oldItem: ItemEntity, newItem: ItemEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ItemEntity, newItem: ItemEntity) = oldItem == newItem
    }

    class ViewHolder(private val binding: ItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemEntity: ItemEntity, clickListener: ItemClickListener){
            binding.itemEntity = itemEntity
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        if(current != null){
            holder.bind(current, clickListener)
        }
    }
}

class ItemClickListener(val clickListener: (itemEntity: ItemEntity) -> Unit){
    fun onClick(itemEntity: ItemEntity) = clickListener(itemEntity)
}