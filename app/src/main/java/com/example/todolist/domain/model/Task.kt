package com.example.todolist.domain.model

import com.example.todolist.data.local.TaskEntity

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val priority: Int = 0, // 0 - низкий, 1 - средний, 2 - высокий
    val deadline: Long? = 1700000000000, // Timestamp (миллисекунды)
    val imageUri: String? = null,
    val videoUri: String? = null,
    val link: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)


fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        priority = priority,
        deadline = deadline,
        imageUri = imageUri,
        videoUri = videoUri,
        link = link,
        createdAt = createdAt
    )
}
fun TaskEntity.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        priority = priority,
        deadline = deadline,
        imageUri = imageUri,
        videoUri = videoUri,
        link = link,
        createdAt = createdAt
    )
}

