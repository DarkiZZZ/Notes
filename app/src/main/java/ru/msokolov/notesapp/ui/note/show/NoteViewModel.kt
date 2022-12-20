package ru.msokolov.notesapp.ui.note.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.msokolov.notesapp.data.NoteRepository
import ru.msokolov.notesapp.data.room.NoteEntity
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository : NoteRepository
) : ViewModel(){

    val getAllTasks = repository.getAllTasks()
    val getAllPriorityTasks = repository.getAllPriorityTasks()

    fun insert(noteEntity: NoteEntity) = viewModelScope.launch {
        repository.insert(noteEntity)
    }

    fun delete(noteEntity: NoteEntity) = viewModelScope.launch{
        repository.deleteItem(noteEntity)
    }

    fun update(noteEntity: NoteEntity) = viewModelScope.launch{
        repository.updateData(noteEntity)
    }

    fun deleteAll() = viewModelScope.launch{
        repository.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<NoteEntity>> {
        return repository.searchDatabase(searchQuery)
    }

}