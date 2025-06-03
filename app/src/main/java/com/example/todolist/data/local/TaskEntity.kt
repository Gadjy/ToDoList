package com.example.todolist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val description: String? = null,
    val priority: Int = 0,
    val deadline: Long? = null,
    val imageUri: String? = null,
    val videoUri: String? = null,
    val link: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

