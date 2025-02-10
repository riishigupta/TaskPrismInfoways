package com.hdbfs.prisminfowaystask.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String?,
    val dueDate: String?,
    val priority: Int,  // 1: High, 2: Medium, 3: Low
    val isCompleted: Boolean = false
)
