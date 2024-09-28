package com.mumayank.airwebview.example.app.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.mumayank.airwebview.example.app.compose.ui.theme.AirWebViewExampleTheme
import com.mumayank.airwebview.example.app.view.ViewActivity

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AirWebViewExampleTheme {
                Column(
                    Modifier.fillMaxSize()
                ) {
                    Button(
                        onClick = {
                            startActivity(Intent(this@HomeActivity, ComponentActivity::class.java))
                        }
                    ) {
                        Text(text = "Compose")
                    }
                    Button(
                        onClick = {
                            startActivity(Intent(this@HomeActivity, ViewActivity::class.java))
                        }
                    ) {
                        Text(text = "View")
                    }
                }
            }
        }
    }
}