package com.devx.utils

import io.ktor.util.*
import io.ktor.utils.io.charsets.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object Encrypt {
    private val key = System.getenv("HASH_KEY").toByteArray()
    private val hmacKey = SecretKeySpec(key, "HmacSHA256")

    fun hash(input: String): String {
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(hmacKey)
        return hex(mac.doFinal(input.toByteArray(Charsets.UTF_8)))
    }

    fun match(input: String, encoded: String): Boolean {
        val encodedInput = hash(input)
        return encodedInput == encoded
    }
}
