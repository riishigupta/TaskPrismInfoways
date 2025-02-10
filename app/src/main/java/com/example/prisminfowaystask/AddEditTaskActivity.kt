package com.hdbfs.prisminfowaystask

import android.app.DatePickerDialog
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.hdbfs.prisminfowaystask.model.Task
import com.hdbfs.prisminfowaystask.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.PrismInfoWaysTask.databinding.ActivityAddEditTaskBinding

class AddEditTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditTaskBinding
    private var dueDate: Long = 0
    private val taskViewModel: TaskViewModel by viewModels()
    private var taskId: Int? = null  // Used for editing tasks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize UI elements


        // Handle Due Date Picker
        binding.btnPickDueDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, day ->
                calendar.set(year, month, day)
                dueDate = calendar.timeInMillis
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
                binding.tvSelectedDate.text = formattedDate
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Check if we are editing an existing task
        taskId = intent.getIntExtra("taskId", -1).takeIf { it != -1 }
        if (taskId != null) {
            binding.editTextTitle.setText(intent.getStringExtra("title"))
            binding.editTextDescription.setText(intent.getStringExtra("description"))
            dueDate = intent.getLongExtra("dueDate", 0)
            binding.spinnerPriority.setSelection(intent.getIntExtra("priority", 2))  // Default to Medium
        }

        // Save Button Click Listener



                binding.btnSaveTask.setOnClickListener {
                    // Retrieve values from views
                    val title = binding.editTextTitle.text.toString().trim()
                    val description = binding.editTextDescription.text.toString().trim()
                    val dueDateString = binding.tvSelectedDate.text.toString().trim()
                    val priority = binding.spinnerPriority.selectedItemPosition
                    val dueDate = binding.tvSelectedDate.text.toString().trim()

                    // Build a list of missing fields
                    val missingFields = mutableListOf<String>()
                    if (title.isEmpty()) {
                        missingFields.add("Task Title")
                    }
                    if (description.isEmpty()) {
                        missingFields.add("Task Description")
                    }
                    if (dueDateString.isEmpty()) {
                        missingFields.add("Due Date")
                    }
                    // Assuming index 0 is a placeholder like "Select Priority"
                    if (priority == 0) {
                        missingFields.add("Priority")
                    }

                    // If any field is missing, show an error dialog
                    if (missingFields.isNotEmpty()) {
                        val message = "Please fill in the following fields: " + missingFields.joinToString(", ")
                        AlertDialog.Builder(this)
                            .setTitle("Incomplete Fields")
                            .setMessage(message)
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                        return@setOnClickListener
                    }

                    // Convert dueDateString to Long (timestamp)


                    // Create the new Task object
                    val newTask = Task(
                        id = taskId ?: 0, // Use existing ID if editing
                        title = title,
                        description = description,
                        dueDate = dueDate,
                        priority = priority,
                        isCompleted = false
                    )

                    // Insert or update the task via ViewModel
                    if (taskId != null) {
                        taskViewModel.update(newTask)
                    } else {
                        taskViewModel.insert(newTask)
                    }

                    finish() // Close the activity
                }



        // Cancel Button Click Listener
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

}
