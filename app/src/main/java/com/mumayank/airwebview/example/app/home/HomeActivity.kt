package com.mumayank.airwebview.example.app.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                    Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Row(Modifier.fillMaxWidth()) {
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
                        Spacer(modifier = Modifier.size(8.dp))
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
                                                AppConstants.PDF_URL
                                            )
                                        )
                                    )
                                )
                            }
                        ) {
                            Text(text = "Compose: PDF")
                        }
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Row(Modifier.fillMaxWidth()) {
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
                        Spacer(modifier = Modifier.size(8.dp))
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


                    Spacer(modifier = Modifier.size(32.dp))
                    Text(text = "With custom WebView/ PDFView:")
                    Spacer(modifier = Modifier.size(16.dp))

                    Row(Modifier.fillMaxWidth()) {
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
                                            ),
                                            Pair(
                                                AppConstants.IS_CUSTOM_VIEW,
                                                true
                                            )
                                        )
                                    )
                                )
                            }
                        ) {
                            Text(text = "Compose: Website")
                        }
                        Spacer(modifier = Modifier.size(8.dp))
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
                                                AppConstants.PDF_URL
                                            ),
                                            Pair(
                                                AppConstants.IS_CUSTOM_VIEW,
                                                true
                                            )
                                        )
                                    )
                                )
                            }
                        ) {
                            Text(text = "Compose: PDF")
                        }
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {
                                startActivity(
                                    Intent(this@HomeActivity, ViewActivity::class.java).putExtras(
                                        bundleOf(
                                            Pair(
                                                AppConstants.URL_DATA,
                                                AppConstants.WEBSITE_URL
                                            ),
                                            Pair(
                                                AppConstants.IS_CUSTOM_VIEW,
                                                true
                                            )
                                        )
                                    )
                                )
                            }
                        ) {
                            Text(text = "View: Website")
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Button(
                            onClick = {
                                startActivity(
                                    Intent(this@HomeActivity, ViewActivity::class.java).putExtras(
                                        bundleOf(
                                            Pair(
                                                AppConstants.URL_DATA,
                                                AppConstants.PDF_URL
                                            ),
                                            Pair(
                                                AppConstants.IS_CUSTOM_VIEW,
                                                true
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
}