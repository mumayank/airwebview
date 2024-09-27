package com.mumayank.airwebview.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.mumayank.airwebview.AirWebViewPdf
import com.mumayank.airwebview.AirWebViewWebsite
import com.mumayank.airwebview.example.app.ui.theme.AirWebViewExampleTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.loadUrl(this, "https://google.com/")
        setContent {
            val mainStatus by viewModel.mainStatus.observeAsState()
            AirWebViewExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (mainStatus) {
                        MainStatus.Error -> {
                            // do something
                        }

                        is MainStatus.PdfFile -> {
                            AirWebViewPdf(
                                Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                (mainStatus as MainStatus.PdfFile).file,
                                onLoaded = {
                                    // do something
                                },
                                onError = {
                                    // do something
                                }
                            )
                        }

                        is MainStatus.WebsiteUrl -> {
                            AirWebViewWebsite(
                                Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                (mainStatus as MainStatus.WebsiteUrl).url,
                                onLoaded = {
                                    // do something
                                },
                                onError = {
                                    // do something
                                }
                            )
                        }

                        null -> {
                            // do nothing
                        }
                    }
                }
            }
        }
    }
}