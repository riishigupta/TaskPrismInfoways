package com.hdbfs.prisminfowaystask.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hdbfs.prisminfowaystask.model.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM task_table ORDER BY dueDate ASC")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE title LIKE :query OR description LIKE :query")
    fun searchTasks(query: String): LiveData<List<Task>>
}
