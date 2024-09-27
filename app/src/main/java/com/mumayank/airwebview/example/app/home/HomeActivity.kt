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
import androidx.core.os.bundleOf
import com.mumayank.airwebview.example.app.AppConstants
import com.mumayank.airwebview.example.app.compose.ComposeActivity
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
                            startActivity(
                                Intent(
                                    this@HomeActivity,
                                    ComposeActivity::class.java
                                ).putExtras(
                                    bundleOf(
                                        Pair(
                                            AppConstants.URL_DATA,
                                            AppConstants.WEBSITE_URL
                                        )
                                    )
                                )
                            )
                        }
                    ) {
                        Text(text = "Compose: Website")
                    }
                    Button(
                        onClick = {
                            startActivity(
                                Intent(this@HomeActivity, ComposeActivity::class.java).putExtras(
                                    bundleOf(
                                        Pair(
                                            AppConstants.URL_DATA,
                                            AppConstants.PDF_URL
                                        )
                                    )
                                )
                            )
                        }
                    ) {
                        Text(text = "Compose: PDF")
                    }
                    Button(
                        onClick = {
                            startActivity(
                                Intent(this@HomeActivity, ViewActivity::class.java).putExtras(
                                    bundleOf(
                                        Pair(
                                            AppConstants.URL_DATA,
                                            AppConstants.WEBSITE_URL
                                        )
                                    )
                                )
                            )
                        }
                    ) {
                        Text(text = "View: Website")
                    }
                    Button(
                        onClick = {
                            startActivity(
                                Intent(this@HomeActivity, ViewActivity::class.java).putExtras(
                                    bundleOf(
                                        Pair(
                                            AppConstants.URL_DATA,
                                            AppConstants.PDF_URL
                                        )
                                    )
                                )
                            )
                        }
                    ) {
                        Text(text = "View: PDF")
                    }
                }
            }
        }
    }
}