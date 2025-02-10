package com.hdbfs.prisminfowaystask.viewmodel
import android.app.Application
import androidx.lifecycle.*
import com.hdbfs.prisminfowaystask.database.TaskDatabase
import com.hdbfs.prisminfowaystask.model.Task
import com.hdbfs.prisminfowaystask.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
    }

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        repository.update(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }

    fun searchTasks(query: String): LiveData<List<Task>> {
        return repository.searchTasks(query)
    }
}
