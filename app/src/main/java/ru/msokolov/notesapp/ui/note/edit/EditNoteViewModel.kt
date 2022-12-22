package ru.msokolov.notesapp.ui.note.edit

import androidx.lifecycle.LiveData
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
class EditNoteViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val noteRepository: NoteRepository
) : ViewModel(){


    fun getAllItemsByNoteId(noteId: Int) = itemRepository.getAllItemsByNoteId(noteId)

    fun editItem(itemEntity: ItemEntity) = viewModelScope.launch {
        itemRepository.insertOrUpdate(itemEntity)
    }

    fun deleteItem(itemEntity: ItemEntity) = viewModelScope.launch {
        itemRepository.deleteItem(itemEntity)
    }

    fun editNote(noteEntity: NoteEntity) = viewModelScope.launch {
        noteRepository.insertOrUpdate(noteEntity)
    }

    fun getLastFetchedNote(): LiveData<NoteEntity> {
        return noteRepository.getLastFetchedNote()
    }

    fun deleteNote(noteEntity: NoteEntity) = viewModelScope.launch {
        noteRepository.deleteNote(noteEntity)
        itemRepository.deleteAllByNoteId(noteEntity.id)
    }
}