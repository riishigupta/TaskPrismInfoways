package com.hdbfs.prisminfowaystask

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.PrismInfoWaysTask.databinding.ActivityMainBinding
import com.example.infoprismatask.adapter.TaskAdapter

import com.hdbfs.prisminfowaystask.model.Task
import com.hdbfs.prisminfowaystask.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe LiveData from ViewModel

        taskViewModel.allTasks.observe(this) { tasks ->
            taskAdapter = TaskAdapter(tasks, ::onTaskChecked, ::onDeleteClicked, ::onTaskClicked)
            binding.recyclerView.adapter = taskAdapter
        }

        // Handle FAB click
        binding.fabAddTask.setOnClickListener {
            val intent = Intent(this, AddEditTaskActivity::class.java)
            startActivity(intent)
        }
    }
    private fun onTaskClicked(task: Task) {
        val intent = Intent(this, AddEditTaskActivity::class.java).apply {
            putExtra("taskId", task.id)
            putExtra("title", task.title)
            putExtra("description", task.description)
            putExtra("dueDate", task.dueDate)
            putExtra("priority", task.priority)
        }
        startActivity(intent)
    }

    private fun onTaskChecked(task: Task) {
        taskViewModel.update(task)
    }

    private fun onDeleteClicked(task: Task) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Confirmation")
        builder.setMessage("Are you sure you want to delete this task?")

        // Set up the "Yes" button to delete the task
        builder.setPositiveButton("Yes") { dialog, _ ->
            taskViewModel.delete(task)
            dialog.dismiss()
        }

        // Set up the "No" button to dismiss the dialog
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        // Create and show the dialog
        val dialog = builder.create()
        dialog.show()
    }
}
