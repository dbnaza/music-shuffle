package com.dbnaza.shufflesongs.network.interactors

import timber.log.Timber
import java.io.*
import java.lang.Exception

fun loadJsonFromResources(classLoader: ClassLoader, fileName: String): String {

    val url = classLoader.getResource(fileName)
    val file = File(url.path)
    return file.readContent()

}

fun File.readContent(): String {

    var content = ""
    try {
        val inputStream = FileInputStream(this)

        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var receiveString = bufferedReader.readLine()

        while (receiveString != null) {
            content += receiveString
            receiveString = bufferedReader.readLine()
        }
        inputStream.close()


    } catch (e: Exception) {
        Timber.d(e)
    }

    return content
}