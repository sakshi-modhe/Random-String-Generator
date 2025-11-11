package com.alpha.randomstringgenerator.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

    fun formatCreatedDate(isoDate: String?): String {
        if (isoDate.isNullOrEmpty()) return "Unknown"

        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            parser.timeZone = TimeZone.getTimeZone("UTC")

            val date = parser.parse(isoDate)

            val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm:ss a", Locale.getDefault())
            formatter.format(date!!)
        } catch (_: Exception) {
            isoDate
        }
    }
