package com.tsato.util

import web.navigator.navigator

fun detectBrowser(): String {
    return when {
        navigator.userAgent.indexOf("Opera") != -1 || navigator.userAgent.indexOf("OPR") != -1 -> "Opera"
        navigator.userAgent.indexOf("Edg") != -1 -> "Edge"
        navigator.userAgent.indexOf("Chrome") != -1 -> "Chrome"
        navigator.userAgent.indexOf("Safari") != -1 -> "Safari"
        navigator.userAgent.indexOf("Firefox") != -1 -> "Firefox"
        navigator.userAgent.indexOf("MSIE") != -1 -> "IE"
        else -> "Unknown"
    }
}