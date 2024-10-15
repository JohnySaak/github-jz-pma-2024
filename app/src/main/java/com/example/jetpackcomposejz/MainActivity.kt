@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jetpackcomposejz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExample()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeExample() {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var place by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Moje Aplikace", color = Color.White) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.DarkGray
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Jméno") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = surname,
                onValueChange = { surname = it },
                label = { Text("Příjmení") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = age,
                onValueChange = {
                    if (it.all { char -> char.isDigit() } && it.toIntOrNull()?.let { it <= 150 } == true) {
                        age = it
                    }
                },
                label = { Text("Věk (hodnota menší než 151)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = place,
                onValueChange = { place = it },
                label = { Text("Bydliště") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    try {
                        // Log the input data
                        Log.d("MainActivity", "Navigating with data: name=$name, surname=$surname, age=$age, place=$place")

                        // Create an intent to navigate to SecondActivity
                        val intent = Intent(context, SecondActivity::class.java).apply {
                            putExtra("name", name)
                            putExtra("surname", surname)
                            putExtra("age", age)
                            putExtra("place", place)
                        }

                        // Show a toast to confirm navigation
                        Toast.makeText(context, "Navigating to Second Activity", Toast.LENGTH_SHORT).show()

                        // Start the second activity
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Log.e("MainActivity", "Error while navigating: ${e.message}")
                        Toast.makeText(context, "Error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Přejít na Druhou Aktivitu")
            }
        }
    }
}
