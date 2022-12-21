package ru.msokolov.notesapp.ui.note.show

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.msokolov.notesapp.data.room.note.NoteEntity
import ru.msokolov.notesapp.databinding.NoteLayoutBinding

class NoteAdapter(private val clickListener: NoteClickListener):
    ListAdapter<NoteEntity, NoteAdapter.ViewHolder>(TaskDiffCallback) {

    companion object TaskDiffCallback : DiffUtil.ItemCallback<NoteEntity>(){
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity) = oldItem == newItem
    }

    class ViewHolder(private val binding: NoteLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(noteEntity: NoteEntity, clickListener: NoteClickListener){
            binding.noteEntity = noteEntity
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        if(current != null){
            holder.bind(current, clickListener)
        }
    }
}

class NoteClickListener(val clickListener: (noteEntity: NoteEntity) -> Unit){
    fun onClick(noteEntry: NoteEntity) = clickListener(noteEntry)
}