package com.mumayank.airwebview.helpers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

object UrlHelper {

    suspend fun getRedictionUrl(initUrl: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(initUrl)
                val connection = (url.openConnection() as HttpURLConnection).apply {
                    instanceFollowRedirects = false
                }
                connection.connect()
                val redirectedUrl = connection.getHeaderField(HEADER_FIELD_LOCATION)
                redirectedUrl ?: initUrl
            } catch (e: Exception) {
                null
            }
        }
    }

    private const val HEADER_FIELD_LOCATION = "Location"
}