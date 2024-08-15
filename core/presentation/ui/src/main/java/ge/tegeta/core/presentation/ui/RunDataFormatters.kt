package ge.tegeta.core.presentation.ui

import android.annotation.SuppressLint
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.time.Duration

@SuppressLint("DefaultLocale")
fun Duration.formatted(): String {
    val totalSeconds = inWholeSeconds
    val hours = String.format("%02d", totalSeconds / (60 * 60))
    val minutes = String.format("%02d", (totalSeconds % (3600)) / 60)
    val seconds = String.format("%02d", (totalSeconds % 60))

    return "$hours:$minutes:$seconds"
}

fun Double.toFormattedKm(): String {
    return "${this.roundToDecimals(1)} km"
}

fun Int.toFormattedMeters(): String {
    return "$this m"
}

private fun Double.roundToDecimals(decimals: Int): Double {
    val factor = 10f.pow(decimals)
    return round(this * factor) / factor
}

fun Double.toFormattedKmh(): String {
    return "${roundToDecimals(1)} km/h"
}

@SuppressLint("DefaultLocale")
fun Duration.toFormattedPace(distance: Double): String {
    if (this == Duration.ZERO || distance <= 0.0)
        return "-"

    val secondsPerKm = (this.inWholeSeconds/distance).roundToInt()
    val avgPaceMinutes = secondsPerKm / 60
    val avgPaceSeconds = String.format("%02d", secondsPerKm % 60)

    return "$avgPaceMinutes:$avgPaceSeconds/km"
}

