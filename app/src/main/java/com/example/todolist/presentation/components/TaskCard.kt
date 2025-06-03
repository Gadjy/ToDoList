package com.example.todolist.presentation.components

import android.content.Intent
import androidx.compose.ui.graphics.Color
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.todolist.domain.model.Task
import com.example.todolist.presentation.viewmodel.TaskViewModel
import kotlinx.coroutines.delay
import okhttp3.internal.wait
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TaskCard(
    task: Task,
    modifier: Modifier = Modifier,
    currentTime: Long,
    viewModel: TaskViewModel,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val now = remember { mutableStateOf(System.currentTimeMillis()) }


    val timeLeft = remember(task.deadline, currentTime) {
        task.deadline?.let {
            getTimeUntilDeadline(it, currentTime)
        } ?: "Нет дедлайна"
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = getPriorityColor(task.priority)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )


                IconButton(onClick = { viewModel.deleteTask(task) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }

            }

            task.description?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (task.deadline != null) {
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.Start
                ) {
                    if (task.createdAt != null) {
                        Text(
                            text = formatDate(task.createdAt),
                            style = MaterialTheme.typography.bodySmall,
                            color = colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "Осталось: $timeLeft",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = formatDate(task.deadline),
                        style = MaterialTheme.typography.bodySmall,
                        color = colorScheme.onSurfaceVariant
                    )




                }
            }



            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                if (!task.imageUri.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = rememberAsyncImagePainter(model = task.imageUri),
                        contentDescription = "Task Image",
                        modifier = Modifier
                            .height(150.dp)
                            .width(150.dp)
                            .background(
                                colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(36.dp)
                            ),
                        contentScale = ContentScale.Crop,

                        )
                }
            }


            if (!task.link.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "🔗 Открыть ссылку",
                    style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(task.link))
                            context.startActivity(intent)
                        }
                )
            }


        }
    }
}


@Composable
fun getPriorityColor(priority: Int): Color {
    return when (priority) {
        2 -> MaterialTheme.colorScheme.errorContainer // Высокий приоритет (красный)
        1 -> MaterialTheme.colorScheme.tertiaryContainer // Средний (жёлтый/оранжевый)
        else -> MaterialTheme.colorScheme.secondaryContainer // Низкий (зелёный/голубой)
    }
}
fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
fun getTimeUntilDeadline(deadline: Long, now: Long): String {
    val diff = deadline - now

    if (diff <= 0) return "⏰ Время вышло"

    val seconds = diff / 1000 % 60
    val minutes = diff / (1000 * 60) % 60
    val hours = diff / (1000 * 60 * 60) % 24
    val days = diff / (1000 * 60 * 60 * 24)

    return buildString {
        if (days > 0) append("$days д. ")
        if (hours > 0 || days > 0) append("$hours ч. ")
        if (minutes > 0 || hours > 0 || days > 0) append("$minutes мин. ")
        append("$seconds сек.")
    }
}




