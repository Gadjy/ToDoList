package com.example.todolist.data.model

import com.example.todolist.domain.model.Task

data class TaskDto(
    val id: Int = 0,
    val title: String = "",
    val description: String? = null,
    val priority: Int = 0,
    val deadline: Long? = null,
    val imageUrl: String? = null,  // используем imageUrl вместо imageUri
    val videoUrl: String? = null,
    val link: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
fun Task.toDto(): TaskDto {
    return TaskDto(
        id = id,
        title = title,
        description = description,
        priority = priority,
        deadline = deadline,
        imageUrl = imageUri,  // маппим напрямую
        videoUrl = videoUri,
        link = link,
        createdAt = createdAt
    )
}
fun TaskDto.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        priority = priority,
        deadline = deadline,
        imageUri = imageUrl,  // маппим обратно
        videoUri = videoUrl,
        link = link,
        createdAt = createdAt
    )
}

