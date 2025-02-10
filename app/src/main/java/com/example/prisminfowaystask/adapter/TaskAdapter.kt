package com.example.infoprismatask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.PrismInfoWaysTask.R
import com.example.PrismInfoWaysTask.databinding.RvItemTaskBinding

import com.hdbfs.prisminfowaystask.model.Task
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(
    private val tasks: List<Task>,
    private val onTaskChecked: (Task) -> Unit,
    private val onDeleteClicked: (Task) -> Unit,
    private val onTaskClicked: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(private val binding: RvItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.taskTitle.text = task.title
            binding.taskDueDate.text = "Due: ${task.dueDate}"
            binding.checkBoxComplete.isChecked = task.isCompleted


            val backgroundColorRes = when (task.priority) {
                1 -> R.color.low_priority
                2 -> R.color.medium_priority
                3 -> R.color.high_priority
                else -> R.color.default_priority
            }
            binding.root.setBackgroundColor(
                ContextCompat.getColor(binding.root.context, backgroundColorRes)
            )

            binding.checkBoxComplete.setOnCheckedChangeListener { _, isChecked ->
                onTaskChecked(task.copy(isCompleted = isChecked))
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClicked(task)
            }

            binding.root.setOnClickListener {
                onTaskClicked(task)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = RvItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size
}
