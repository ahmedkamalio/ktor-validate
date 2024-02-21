package com.amedmoore.ktor.validate.utils

import java.net.URLDecoder
import java.net.URLEncoder

fun Any?.isString() = this is String

fun String.toUrlEncoded() = URLEncoder.encode(this, "UTF-8").orEmpty()

fun String.toUrlDecoded() = URLDecoder.decode(this, "UTF-8").orEmpty()

fun String.isByteLength(min: Int = 0, max: Int? = null): Boolean {
    val len = toUrlEncoded().split(Regex("%..|.")).size - 1
    return len >= min && (max == null || len <= max)
}
