package com.dbnaza.shufflesongs.testutils

import kotlin.random.Random

object StringGenerator {
    private const val ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm"

    fun getRandomString(sizeOfRandomString: Int = 15): String {
        val sb = StringBuilder(sizeOfRandomString)
        for (i in 0 until sizeOfRandomString)
            sb.append(ALLOWED_CHARACTERS[Random.nextInt(ALLOWED_CHARACTERS.length)])
        return sb.toString()
    }
}