package ru.msokolov.notesapp.ui.note.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.msokolov.notesapp.data.room.note.NoteRepository
import ru.msokolov.notesapp.data.room.note.NoteEntity
import javax.inject.Inject

@HiltViewModel
class NoteShowViewModel @Inject constructor(
    private val noteRepository : NoteRepository
) : ViewModel(){


    val getAllNotes = noteRepository.getAllNotes()
    val getAllPriorityNotes = noteRepository.getAllPriorityNotes()

    fun insertNote(noteEntity: NoteEntity) = viewModelScope.launch {
        noteRepository.insert(noteEntity)
    }

    fun deleteNote(noteEntity: NoteEntity) = viewModelScope.launch{
        noteRepository.deleteItem(noteEntity)
    }

    fun updateNote(noteEntity: NoteEntity) = viewModelScope.launch{
        noteRepository.updateData(noteEntity)
    }

    fun deleteAllNotes() = viewModelScope.launch{
        noteRepository.deleteAll()
    }

    fun searchDatabaseForNotes(searchQuery: String): LiveData<List<NoteEntity>> {
        return noteRepository.searchDatabase(searchQuery)
    }

}