package com.mumayank.airwebview.example.app.compose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mumayank.airwebview.AirWebView
import com.mumayank.airwebview.example.app.AppConstants
import com.mumayank.airwebview.example.app.compose.ui.theme.AirWebViewExampleTheme

class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            var isProgress by remember { mutableStateOf(true) }
            AirWebViewExampleTheme {
                AirWebView(
                    Modifier.fillMaxSize(),
                    intent.getStringExtra(AppConstants.URL_DATA).toString(),
                    onProgressChange = {
                        isProgress = it
                    },
                    onError = {
                        onError(context)
                    }
                )
                if (isProgress) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .clickable { }
                    ) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }

    private fun onError(context: Context) {
        Toast.makeText(
            context,
            "Something went wrong",
            Toast.LENGTH_SHORT
        ).show()
    }
}