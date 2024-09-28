package com.mumayank.airwebview.helpers

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object FileDownloadHelper {

    suspend fun getPdfFile(context: Context, inputUrl: String): File? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(inputUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.requestMethod = REQUEST_METHOD
                connection.connect()
                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val file = File(context.cacheDir, System.currentTimeMillis().toString())
                    val fileOutput = FileOutputStream(file)
                    val inputStream: InputStream = connection.inputStream
                    val buffer = ByteArray(BYTE_ARRAY_SIZE)
                    var length: Int
                    while (inputStream.read(buffer).also { length = it } > ZERO) {
                        fileOutput.write(buffer, ZERO, length)
                    }
                    fileOutput.close()
                    inputStream.close()
                    file
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    private const val REQUEST_METHOD = "GET"
    private const val BYTE_ARRAY_SIZE = 1024
    private const val ZERO = 0
}