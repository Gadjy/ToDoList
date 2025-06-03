package com.example.todolist.di

import android.content.Context
import androidx.room.Room
import com.example.todolist.data.local.AppDatabase
import com.example.todolist.data.local.TaskDao
import com.example.todolist.data.repository.TaskRepositoryImpl
import com.example.todolist.domain.repository.TaskRepository
import com.example.todolist.domain.usecase.DeleteAllTasksUseCase
import com.example.todolist.domain.usecase.AddTaskUseCase
import com.example.todolist.domain.usecase.UpdateTaskUseCase
import com.example.todolist.domain.usecase.GetAllTasksUseCase
import com.example.todolist.domain.usecase.GetTaskByIdUseCase
import com.example.todolist.domain.usecase.DeleteTaskUseCase
import com.example.todolist.domain.usecase.UseCases
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "task_database"
        ).build()
    }

    @Provides
    fun provideTaskDao(db: AppDatabase): TaskDao = db.taskDao()





    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository): UseCases {
        return UseCases(
            addTask = AddTaskUseCase(repository),
            getAllTasks = GetAllTasksUseCase(repository),
            getTaskById = GetTaskByIdUseCase(repository),
            updateTask = UpdateTaskUseCase(repository),
            deleteTask = DeleteTaskUseCase(repository),
            deleteAllTasks = DeleteAllTasksUseCase(repository)
        )
    }

}

