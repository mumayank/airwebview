package com.mumayank.airwebview.example.app.compose

import java.io.File

sealed class ComposeStatus {
    data class WebsiteUrl(val url: String) : ComposeStatus()
    data class PdfFile(val file: File) : ComposeStatus()
    object Error : ComposeStatus()
}