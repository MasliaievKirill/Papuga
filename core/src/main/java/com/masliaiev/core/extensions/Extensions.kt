package com.masliaiev.core.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.masliaiev.core.constants.EmptyConstants.EMPTY_STRING
import kotlin.math.ceil
import kotlin.time.Duration

fun Bundle?.getStringOrEmpty(key: String): String {
    return this?.getString(key, EMPTY_STRING) ?: EMPTY_STRING
}

fun Duration.toFormattedSeconds(): String {
    return this.inWholeSeconds.toInt().let {
        if (it < TEN_SECONDS) {
            ONE_NUMBER_TIME_FORMAT + it
        } else {
            TWO_NUMBER_TIME_FORMAT + it
        }
    }
}

fun Float.roundProgress(): Float {
    val number = (ceil(this@roundProgress.toDouble() * ROUNDING_SCALE) / ROUNDING_SCALE)
    val string = number.toString()
    return when {
        string.length > 3 && string.last().digitToInt() <= 3 -> string.dropLast(1)
        string.length > 3 && string.last().digitToInt() in 4..6 -> string.dropLast(1) + "4"
        string.length > 3 && string.last().digitToInt() > 6 -> string.dropLast(1) + "7"
        else -> string
    }.toFloat()
}

fun Activity.onShareClick(url: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}

private const val TEN_SECONDS = 10
private const val ONE_NUMBER_TIME_FORMAT = "0:0"
private const val TWO_NUMBER_TIME_FORMAT = "0:"
private const val ROUNDING_SCALE = 100