package io.droidevs.taskjournal.data.preference.delegate

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import io.droidevs.taskjournal.data.preference.exceptions.flowCatchingPreference
import io.droidevs.taskjournal.data.preference.exceptions.runCatchingPreference
import io.droidevs.taskjournal.data.preference.exceptions.runCatchingPreferenceWithResult
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Generic delegate wrapping a single [DataStore] preference key.
 *
 * Every `XxxPreference` implementation should own exactly one of these,
 * scoped to a single [Preferences.Key]. This avoids reading/writing the
 * entire [AppPreferences][io.droidevs.taskjournal.domain.model.AppPreferences]
 * blob just to change one value.
 *
 * @param T The domain type exposed to callers (e.g. [Boolean], [Int], or an enum mapped to [R]).
 * @param R The raw type actually stored in DataStore (e.g. [String], [Int], [Boolean]).
 */
class PreferenceDelegate<T, R>(
    private val dataStore: DataStore<Preferences>,
    private val key: Preferences.Key<R>,
    private val defaultValue: T,
    private val toRaw: (T) -> R,
    private val fromRaw: (R) -> T,
) {

    val flow: Flow<Result<T, PreferenceError>> by lazy {
        flowCatchingPreference {
            dataStore.data.map { prefs -> prefs[key]?.let(fromRaw) ?: defaultValue }
        }
    }

    suspend fun get(): Result<T, PreferenceError> = runCatchingPreferenceWithResult {
        flow.first()
    }

    suspend fun set(value: T): Result<T, PreferenceError> = runCatchingPreference {
        dataStore.edit { prefs -> prefs[key] = toRaw(value) }
        value
    }
}

/**
 * Convenience constructor for preferences whose domain type already matches
 * the raw DataStore type (e.g. [Boolean], [Int], [String]) — no mapping needed.
 */
fun <T> identityPreferenceDelegate(
    dataStore: DataStore<Preferences>,
    key: Preferences.Key<T>,
    defaultValue: T,
): PreferenceDelegate<T, T> = PreferenceDelegate(
    dataStore = dataStore,
    key = key,
    defaultValue = defaultValue,
    toRaw = { it },
    fromRaw = { it },
)

/**
 * Convenience constructor for enum-backed preferences stored as their [Enum.name].
 * Falls back to [defaultValue] if the stored name no longer maps to a valid enum constant
 * (e.g. after a rename or removal).
 */
inline fun <reified E : Enum<E>> enumPreferenceDelegate(
    dataStore: DataStore<Preferences>,
    key: Preferences.Key<String>,
    defaultValue: E,
): PreferenceDelegate<E, String> = PreferenceDelegate(
    dataStore = dataStore,
    key = key,
    defaultValue = defaultValue,
    toRaw = { it.name },
    fromRaw = { raw -> runCatching { enumValueOf<E>(raw) }.getOrDefault(defaultValue) },
)

/**
 * Delegate for an optional [Long] preference (e.g. a nullable foreign-key-like setting
 * such as "default category id"). Unlike [PreferenceDelegate], a missing key correctly
 * maps to `null` rather than a sentinel value, since [Preferences.Key] lookups are
 * naturally optional.
 */
class NullableLongPreferenceDelegate(
    private val dataStore: DataStore<Preferences>,
    private val key: Preferences.Key<Long>,
) {

    val flow: Flow<Result<Long?, PreferenceError>> by lazy {
        flowCatchingPreference {
            dataStore.data.map { prefs -> prefs[key] }
        }
    }

    suspend fun get(): Result<Long?, PreferenceError> = runCatchingPreferenceWithResult {
        flow.first()
    }

    suspend fun set(value: Long?): Result<Long?, PreferenceError> = runCatchingPreference {
        dataStore.edit { prefs ->
            if (value == null) prefs.remove(key) else prefs[key] = value
        }
        value
    }
}