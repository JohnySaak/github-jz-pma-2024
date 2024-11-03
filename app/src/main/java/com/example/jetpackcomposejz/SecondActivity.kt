@file:OptIn(ExperimentalMaterial3Api::class)


package com.example.jetpackcomposejz

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.remember

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            // Retrieve the data from Intent and handle possible null values
            val name = intent.getStringExtra("name") ?: "Unknown"
            val surname = intent.getStringExtra("surname") ?: "Unknown"
            val age = intent.getStringExtra("age") ?: "Unknown"
            val place = intent.getStringExtra("place") ?: "Unknown"

            Log.d("SecondActivity", "Received data: name=$name, surname=$surname, age=$age, place=$place")

            // Display the UI using Jetpack Compose
            setContent {
                SecondScreen(name = name, surname = surname, age = age, place = place)
            }
        } catch (e: Exception) {
            Log.e("SecondActivity", "Error: ${e.message}")
        }
    }



}

@Composable
fun SecondScreen(name: String, surname: String, age: String, place: String) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Druhá Aktivita", color = Color.White) },
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
            Text(text = "Jméno: $name", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Příjmení: $surname", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Věk: $age", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Bydliště: $place", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Navigate back to MainActivity
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    (context as ComponentActivity).finish()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Zpět na hlavní obrazovku")
            }
        }
    }
}

