package ru.msokolov.notesapp.ui.note.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.msokolov.notesapp.data.room.item.ItemEntity
import ru.msokolov.notesapp.data.room.item.ItemRepository
import ru.msokolov.notesapp.data.room.note.NoteEntity
import ru.msokolov.notesapp.data.room.note.NoteRepository
import javax.inject.Inject

@HiltViewModel
class UpdateNoteViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val noteRepository: NoteRepository
) : ViewModel(){

    var currentNoteId: Int = 1

    val getAllItems = itemRepository.getAllItems(currentNoteId)

    fun insertItem(itemEntity: ItemEntity) = viewModelScope.launch {
        itemRepository.insert(itemEntity)
    }

    fun deleteItem(itemEntity: ItemEntity) = viewModelScope.launch {
        itemRepository.deleteItem(itemEntity)
    }

    fun updateItem(itemEntity: ItemEntity) = viewModelScope.launch {
        itemRepository.updateData(itemEntity)
    }

    fun deleteAllItemsOfCurrentNote(currentNoteId: Int) = viewModelScope.launch {
        itemRepository.deleteAllByNoteId(currentNoteId)
    }

    fun editNote(noteEntity: NoteEntity) = viewModelScope.launch {
        noteRepository.insert(noteEntity)
    }
}