package com.hdbfs.prisminfowaystask.repository

import androidx.lifecycle.LiveData
import com.hdbfs.prisminfowaystask.database.TaskDao
import com.hdbfs.prisminfowaystask.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }

    fun searchTasks(query: String): LiveData<List<Task>> {
        return taskDao.searchTasks(query)
    }
}
