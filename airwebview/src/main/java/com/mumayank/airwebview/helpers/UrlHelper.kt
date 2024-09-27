package com.mumayank.airwebview.helpers

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

object UrlHelper {

    suspend fun getRedirectionUrl(initUrl: String): String? {
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
                Log.e("", e.toString())
                null
            }
        }
    }

    private const val HEADER_FIELD_LOCATION = "Location"
}