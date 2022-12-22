package ru.msokolov.notesapp.ui.note.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.msokolov.notesapp.data.room.item.ItemRepository
import ru.msokolov.notesapp.data.room.note.NoteRepository
import ru.msokolov.notesapp.data.room.note.NoteEntity
import javax.inject.Inject

@HiltViewModel
class NoteShowViewModel @Inject constructor(
    private val noteRepository : NoteRepository,
    private val itemRepository: ItemRepository
) : ViewModel(){


    val getAllNotes = noteRepository.getAllNotes()
    val getAllPriorityNotes = noteRepository.getAllPriorityNotes()


    fun editNote(noteEntity: NoteEntity) = viewModelScope.launch {
        noteRepository.insertOrUpdate(noteEntity)
    }

    fun deleteNote(noteEntity: NoteEntity) = viewModelScope.launch{
        noteRepository.deleteNote(noteEntity)
    }

    fun searchDatabaseForNotes(searchQuery: String): LiveData<List<NoteEntity>> {
        return noteRepository.searchDatabase(searchQuery)
    }

    fun deleteAllByNoteId(noteId: Int) = viewModelScope.launch {
        itemRepository.deleteAllByNoteId(noteId)
    }

    fun deleteAllData() = viewModelScope.launch {
        noteRepository.deleteAll()
        itemRepository.deleteAllData()
    }

    suspend fun getLastFetchedNote(): NoteEntity{
        val deferred: Deferred<NoteEntity> = viewModelScope.async {
           noteRepository.getLastFetchedNote()
        }
        return deferred.await()
    }
}