package com.mumayank.airwebview

import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView
import com.mumayank.airwebview.helpers.AirWebViewHelper
import com.mumayank.airwebview.helpers.PdfViewHelper
import com.mumayank.airwebview.helpers.WebViewHelper
import java.io.File

@Composable
fun AirWebView(
    modifier: Modifier = Modifier,
    url: String,
    onProgressChange: ((Boolean) -> Unit)?,
    onError: (() -> Unit)?,
    setCustomWebView: ((WebView) -> WebView?)? = null,
    setCustomPdfView: ((PDFView) -> PDFView?)? = null
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var status by remember { mutableStateOf<Status?>(null) }
    var isOnProgressInvoked by remember { mutableStateOf(false) }
    when (status) {
        is Status.Website -> {
            AndroidView(
                modifier = modifier.fillMaxSize(),
                factory = { context ->
                    val webView = WebView(context)
                    val updatedWebView = WebViewHelper.init(webView, onError, onProgressChange)
                    val customWebView = setCustomWebView?.invoke(webView)
                    val finalWebView = if (customWebView == null) {
                        updatedWebView
                    } else {
                        onProgressUpdate(
                            isOnProgressInvoked = isOnProgressInvoked,
                            onIsOnProgressInvokedUpdate = {
                                isOnProgressInvoked = it
                            },
                            onProgressChange = {
                                onProgressChange?.invoke(it)
                            }
                        )
                        customWebView
                    }
                    finalWebView
                },
                update = { webView ->
                    webView.loadUrl(url)
                }
            )
        }

        is Status.Pdf -> {
            AndroidView(
                modifier = modifier.fillMaxSize(),
                factory = { context ->
                    val pdfView = PDFView(context, null)
                    val updatedPdfView = PdfViewHelper.init(pdfView, (status as Status.Pdf).file)
                    val finalPdfView = setCustomPdfView?.invoke(updatedPdfView) ?: updatedPdfView
                    finalPdfView
                },
                update = { pdfView ->
                    pdfView.fromFile((status as Status.Pdf).file)
                        .onLoad {
                            onProgressUpdate(
                                isOnProgressInvoked = isOnProgressInvoked,
                                onIsOnProgressInvokedUpdate = {
                                    isOnProgressInvoked = it
                                },
                                onProgressChange = {
                                    onProgressChange?.invoke(it)
                                }
                            )
                        }
                        .onError {
                            onError?.invoke()
                        }
                        .onPageError { page, t ->
                            onError?.invoke()
                        }
                        .load()
                }
            )
        }

        else -> {
            // do nothing
        }
    }
    LaunchedEffect(true) {
        onProgressChange?.invoke(true)
        AirWebViewHelper.load(
            context,
            coroutineScope,
            url,
            onWebsiteUrl = {
                status = Status.Website(it)
            },
            onPdfFile = {
                status = Status.Pdf(it)
            },
            onError = {
                onError?.invoke()
            }
        )
    }
}

private fun onProgressUpdate(
    isOnProgressInvoked: Boolean,
    onIsOnProgressInvokedUpdate: ((Boolean) -> Unit),
    onProgressChange: ((Boolean) -> Unit)
) {
    if (isOnProgressInvoked.not()) {
        onIsOnProgressInvokedUpdate.invoke(true)
        onProgressChange.invoke(false)
    }
}

sealed class Status {
    data class Website(val url: String) : Status()
    data class Pdf(val file: File) : Status()
}