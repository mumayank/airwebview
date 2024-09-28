package com.mumayank.airwebview.example.app

import java.io.File

sealed class MainStatus {
    data class WebsiteUrl(val url: String) : MainStatus()
    data class PdfFile(val file: File) : MainStatus()
    object Error : MainStatus()
}