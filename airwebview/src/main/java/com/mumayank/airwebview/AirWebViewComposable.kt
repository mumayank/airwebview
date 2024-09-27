package com.mumayank.airwebview

import android.annotation.SuppressLint
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
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
import com.github.barteksc.pdfviewer.link.DefaultLinkHandler
import com.mumayank.airwebview.helpers.AirWebViewHelper
import com.mumayank.airwebview.helpers.Constants
import java.io.File

@Composable
fun AirWebView(
    modifier: Modifier = Modifier,
    url: String,
    onProgressChange: ((Boolean) -> Unit)?,
    onError: (() -> Unit)?
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
                    WebView(context)
                },
                update = { webView ->
                    androidViewWebView(
                        webView,
                        (status as Status.Website).url,
                        onLoaded = {
                            if (isOnProgressInvoked.not()) {
                                isOnProgressInvoked = true
                                onProgressChange?.invoke(false)
                            }
                        },
                        onError
                    )
                }
            )
        }

        is Status.Pdf -> {
            AndroidView(
                modifier = modifier.fillMaxSize(),
                factory = { context ->
                    PDFView(context, null)
                },
                update = { pdfView ->
                    androidViewPdfView(
                        pdfView,
                        (status as Status.Pdf).file,
                        onLoaded = {
                            if (isOnProgressInvoked.not()) {
                                isOnProgressInvoked = true
                                onProgressChange?.invoke(false)
                            }
                        },
                        onError
                    )
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

@SuppressLint("SetJavaScriptEnabled")
private fun androidViewWebView(
    webView: WebView,
    websiteUrl: String,
    onLoaded: (() -> Unit)?,
    onError: (() -> Unit)?
) {
    with(webView) {
        settings.apply {
            useWideViewPort = true
            loadWithOverviewMode = true
            builtInZoomControls = true
            displayZoomControls = true
            javaScriptEnabled = true
            cacheMode = android.webkit.WebSettings.LOAD_NO_CACHE
        }
        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?,
            ): Boolean {
                loadUrl(request?.url.toString())
                return false
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?,
            ) {
                super.onReceivedError(view, request, error)
                onError?.invoke()
            }

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?,
            ) {
                super.onReceivedHttpError(view, request, errorResponse)
                onError?.invoke()
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?,
            ) {
                super.onReceivedSslError(view, handler, error)
                onError?.invoke()
            }
        }
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == Constants.PROGRESS_COMPLETED) {
                    onLoaded?.invoke()
                }
            }
        }
        loadUrl(websiteUrl)
    }
}

private fun androidViewPdfView(
    pdfView: PDFView,
    file: File,
    onLoaded: (() -> Unit)?,
    onError: (() -> Unit)?
) {
    with(pdfView) {
        visibility = android.view.View.VISIBLE
        this.fromFile(file)
            .enableSwipe(true) // allows to block changing pages using swipe
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .defaultPage(Constants.ZERO)
            .onLoad {
                onLoaded?.invoke()
            }
            .onError {
                onError?.invoke()
            }
            .onPageError { page, t ->
                onError?.invoke()
            }
            //.onRender(onRenderListener) // called after document is rendered for the first time
            .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
            .enableAntialiasing(true) // improve rendering a little bit on low-res screens
            // spacing between pages in dp. To define spacing color, set view background
            .spacing(Constants.ZERO)
            .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
            .linkHandler(DefaultLinkHandler(pdfView))
            .pageFitPolicy(com.github.barteksc.pdfviewer.util.FitPolicy.WIDTH) // mode to fit pages in the view
            .pageSnap(false) // snap pages to screen boundaries
            .pageFling(false) // make a fling change only a single page like ViewPager
            .nightMode(false) // toggle night mode
            .load()
    }
}

sealed class Status {
    data class Website(val url: String) : Status()
    data class Pdf(val file: File) : Status()
}