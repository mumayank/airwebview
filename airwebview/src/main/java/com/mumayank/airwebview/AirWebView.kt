package com.mumayank.airwebview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.webkit.WebView
import android.widget.LinearLayout
import com.github.barteksc.pdfviewer.PDFView
import com.mumayank.airwebview.databinding.AirwebviewLayoutBinding
import com.mumayank.airwebview.helpers.AirWebViewHelper
import com.mumayank.airwebview.helpers.Constants
import com.mumayank.airwebview.helpers.PdfViewHelper
import com.mumayank.airwebview.helpers.WebViewHelper
import kotlinx.coroutines.CoroutineScope

class AirWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = Constants.ZERO,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: AirwebviewLayoutBinding =
        AirwebviewLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    @SuppressLint("SetJavaScriptEnabled")
    fun load(
        context: Context,
        viewModelScope: CoroutineScope,
        url: String,
        onProgressChange: ((Boolean) -> Unit)?,
        onError: (() -> Unit)?,
        setCustomWebView: ((WebView) -> WebView?)? = null,
        setCustomPdfView: ((PDFView) -> PDFView?)? = null
    ) {
        onProgressChange?.invoke(true)
        with(binding) {
            AirWebViewHelper.load(
                context,
                viewModelScope,
                url,
                onWebsiteUrl = { url ->
                    val webView = WebView(context)
                    val updatedWebView = WebViewHelper.init(webView, onError, onProgressChange)
                    val customWebView = setCustomWebView?.invoke(webView)
                    val finalWebView = if (customWebView == null) {
                        updatedWebView
                    } else {
                        onProgressChange?.invoke(false)
                        customWebView
                    }
                    finalWebView.let {
                        parentLayout.addView(it)
                        it.loadUrl(url)
                    }
                },
                onPdfFile = { file ->
                    val pdfView = PDFView(context, null)
                    val updatedPdfView = PdfViewHelper.init(pdfView, file)
                    val finalPdfView = setCustomPdfView?.invoke(updatedPdfView) ?: updatedPdfView
                    finalPdfView.let {
                        parentLayout.addView(it)
                        it.fromFile(file)
                            .onLoad {
                                onProgressChange?.invoke(false)
                            }
                            .onError {
                                onError?.invoke()
                            }
                            .onPageError { page, t ->
                                onError?.invoke()
                            }
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