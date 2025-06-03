package com.example.todolist.presentation.screens
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.todolist.domain.model.Task
import com.example.todolist.presentation.components.formatDate
import com.example.todolist.presentation.viewmodel.TaskViewModel
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    navController: NavController,
    viewModel: TaskViewModel,
    taskId: Int? = null
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val currentTask by viewModel.currentTask.collectAsState()

    var imageUri by rememberSaveable { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val copiedPath = copyImageToInternalStorage(context, it)
            imageUri = copiedPath
        }
    }
    var deadline by rememberSaveable { mutableStateOf<Long?>(null) }

    val datePicker = rememberDatePickerState()
    val showDatePicker = remember { mutableStateOf(false) }




    // Загрузка задачи при редактировании
    LaunchedEffect(taskId) {
        if (taskId != null && taskId != -1) {
            viewModel.loadTask(taskId)
        } else {
            viewModel.clearCurrentTask()
        }
    }

    // Установка данных при редактировании
    LaunchedEffect(currentTask) {
        currentTask?.let {
            title = it.title
            description = it.description ?: ""
            imageUri = it.imageUri
            //deadline = it.deadline
            //createdAt = currentTask?.createdAt ?: System.currentTimeMillis()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (taskId == null) "Новая задача" else "Редактирование") },
                actions = {
                    IconButton(onClick = {
                        if (title.isNotBlank()) {
                            val task = Task(
                                id = currentTask?.id ?: 0,
                                title = title,
                                description = description,
                                imageUri = imageUri,
                                deadline = deadline,
                                createdAt = currentTask?.createdAt ?: System.currentTimeMillis()
                            )

                            if (taskId == null) {
                                viewModel.addTask(task)
                            } else {
                                viewModel.updateTask(task)
                            }
                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Сохранить")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Заголовок") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Описание") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(12.dp))

            if (showDatePicker.value) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker.value = false },
                    confirmButton = {
                        TextButton(onClick = {
                            deadline = datePicker.selectedDateMillis
                            showDatePicker.value = false
                        }) {
                            Text("Ок")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker.value = false }) {
                            Text("Отмена")
                        }
                    }
                ) {
                    DatePicker(state = datePicker)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(onClick = { showDatePicker.value = true }) {
                Text(if (deadline != null) {
                    "Дедлайн: ${formatDate(deadline!!)}"
                } else {
                    "Выбрать дедлайн"
                })
            }

            OutlinedButton(
                onClick = {
                    imagePickerLauncher.launch("image/*")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Выбрать фото")
            }
            imageUri?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Image(
                    painter = rememberAsyncImagePainter(model = it),
                    contentDescription = "Фото задачи",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }


        }
    }
}
fun copyImageToInternalStorage(context: Context, uri: Uri): String {
    val inputStream = context.contentResolver.openInputStream(uri)
    val fileName = "task_image_${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, fileName)
    val outputStream = FileOutputStream(file)
    inputStream?.copyTo(outputStream)
    outputStream.close()
    inputStream?.close()
    return file.absolutePath
}

