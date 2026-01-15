package com.example.viikkotehtava1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viikkotehtava1.ui.theme.Viikkotehtava1Theme
import com.example.viikkotehtava1.domain.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Viikkotehtava1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    override fun onStart() {
        val tag = "MainActivity"
        super.onStart()
        Log.d(tag, "onStart")
    }

    override fun onResume() {
        val tag = "MainActivity"
        super.onResume()
        Log.d(tag, "onResume")
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var tasks by remember { mutableStateOf(MockData.tasks) }
    var showOnlyDone by remember { mutableStateOf(false) }
    var sortByDate by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "My Tasks",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { showOnlyDone = !showOnlyDone }
            ) {
                Text(if (showOnlyDone) "Show all" else "Show only completed")
            }

            Button(
                onClick = { sortByDate = !sortByDate }
            ) {
                Text(if (sortByDate) "Default filter" else "Filter by date")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val filteredTasks = if (showOnlyDone) {
            filterByDone(tasks, true)
        } else {
            tasks
        }

        val sortedTasks = if (sortByDate) {
            sortByDueDate(filteredTasks)
        } else {
            filteredTasks
        }

        LazyColumn {
            items(sortedTasks) { task ->
                TaskItem(
                    task = task,
                    onToggle = {
                        tasks = toggleDone(tasks, task.id)
                    }
                )
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onToggle
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    fontSize = 18.sp
                )
                Text(
                    text = task.description,
                    color = Color.Gray
                )
                Text(
                    text = "Due: ${task.dueDate} | Priority: ${task.priority}"
                )
            }
            Text(
                text = if (task.done) "✓" else "○",
                fontSize = 24.sp,
                color = if (task.done) Color.Green else Color.Gray
            )
        }
    }
}