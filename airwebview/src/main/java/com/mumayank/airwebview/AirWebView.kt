package com.mumayank.airwebview

import android.annotation.SuppressLint
import android.content.Context
import android.net.http.SslError
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.github.barteksc.pdfviewer.link.DefaultLinkHandler
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.mumayank.airwebview.databinding.AirwebviewLayoutBinding
import com.mumayank.airwebview.helpers.AirWebViewHelper
import com.mumayank.airwebview.helpers.Constants
import kotlinx.coroutines.CoroutineScope

class AirWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = Constants.ZERO,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: AirwebviewLayoutBinding =
        AirwebviewLayoutBinding.inflate(LayoutInflater.from(context), this)

    init {
        with(binding) {
            View.GONE.let {
                webView.visibility = it
                pdfView.visibility = it
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun load(
        context: Context,
        viewModelScope: CoroutineScope,
        url: String,
        onProgressChange: ((Boolean) -> Unit)?,
        onError: (() -> Unit)?,
    ) {
        onProgressChange?.invoke(true)
        with(binding) {
            AirWebViewHelper.load(
                context,
                viewModelScope,
                url,
                onWebsiteUrl = {
                    with(webView) {
                        visibility = View.VISIBLE
                        settings.apply {
                            useWideViewPort = true
                            loadWithOverviewMode = true
                            builtInZoomControls = true
                            displayZoomControls = true
                            javaScriptEnabled = true
                            cacheMode = WebSettings.LOAD_NO_CACHE
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
                                    onProgressChange?.invoke(false)
                                }
                            }
                        }
                        loadUrl(url)
                    }
                },
                onPdfFile = {
                    with(pdfView) {
                        visibility = View.VISIBLE
                        this.fromFile(it)
                            .enableSwipe(true) // allows to block changing pages using swipe
                            .swipeHorizontal(false)
                            .enableDoubletap(true)
                            .defaultPage(Constants.ZERO)
                            .onLoad {
                                onProgressChange?.invoke(false)
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
                            .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
                            .pageSnap(false) // snap pages to screen boundaries
                            .pageFling(false) // make a fling change only a single page like ViewPager
                            .nightMode(false) // toggle night mode
                            .load()
                    }
                },
                onError = {
                    onError?.invoke()
                }
            )
        }
    }

}