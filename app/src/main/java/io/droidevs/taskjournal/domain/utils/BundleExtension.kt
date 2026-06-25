package io.droidevs.taskjournal.domain.utils

import android.os.Build
import android.os.Bundle
import java.io.Serializable

internal inline fun <reified T : Serializable> Bundle.getSerializableCompat(key: String): T? {
    return if (Build.VERSION.SDK_INT >= 33) {
        this.getSerializable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        this.getSerializable(key) as T?
    }
}