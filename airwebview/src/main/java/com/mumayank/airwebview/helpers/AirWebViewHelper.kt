package com.mumayank.airwebview.helpers

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

object AirWebViewHelper {

    fun load(
        context: Context,
        viewModelScope: CoroutineScope,
        url: String,
        onWebsiteUrl: ((String) -> Unit)?,
        onPdfFile: ((File) -> Unit)?,
        onError: (() -> Unit)?,
    ) {
        viewModelScope.launch {
            val nextUrl = UrlHelper.getRedirectionUrl(url)
            when {
                nextUrl == null -> {
                    withContext(Dispatchers.Main) {
                        onError?.invoke()
                    }
                }

                nextUrl.endsWith(PDF_SUFFIX) -> {
                    val file = FileDownloadHelper.getPdfFile(context, nextUrl)
                    if (file == null) {
                        withContext(Dispatchers.Main) {
                            onError?.invoke()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            onPdfFile?.invoke(file)
                        }
                    }
                }

                else -> {
                    withContext(Dispatchers.Main) {
                        onWebsiteUrl?.invoke(nextUrl)
                    }
                }
            }
        }
    }

    private const val PDF_SUFFIX = ".pdf"
}