package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.todolist.presentation.navigation.NavGraph
import com.example.todolist.presentation.theme.ToDoListTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoListTheme {
                val windowSizeClass = currentWindowAdaptiveInfo()
                val navController = rememberNavController()
                NavGraph(navController = navController)

            }
        }
    }
}
@Composable
fun TabletScreen(){

    Row {

    }
}

