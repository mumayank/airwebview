package com.mumayank.airwebview.example.app.compose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mumayank.airwebview.AirWebViewPdf
import com.mumayank.airwebview.AirWebViewWebsite
import com.mumayank.airwebview.example.app.AppConstants
import com.mumayank.airwebview.example.app.compose.ui.theme.AirWebViewExampleTheme

class ComposeActivity : ComponentActivity() {

    private val viewModel by viewModels<ComposeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadUrl(this, AppConstants.WEBSITE_URL)
        setContent {
            val context = LocalContext.current
            val mainStatus by viewModel.composeStatus.observeAsState()
            AirWebViewExampleTheme {
                Column(
                    Modifier.fillMaxSize()
                ) {
                    when (mainStatus) {
                        ComposeStatus.Error -> {
                            // do something
                            onError(context)
                        }

                        is ComposeStatus.PdfFile -> {
                            AirWebViewPdf(
                                Modifier
                                    .fillMaxSize(),
                                (mainStatus as ComposeStatus.PdfFile).file,
                                onLoaded = {
                                    // do something
                                },
                                onError = {
                                    // do something
                                    onError(context)
                                }
                            )
                        }

                        is ComposeStatus.WebsiteUrl -> {
                            AirWebViewWebsite(
                                Modifier
                                    .fillMaxSize(),
                                (mainStatus as ComposeStatus.WebsiteUrl).url,
                                onLoaded = {
                                    // do something
                                },
                                onError = {
                                    // do something
                                    onError(context)
                                }
                            )
                        }

                        null -> {
                            CircularProgressIndicator()
                            // do nothing
                        }
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