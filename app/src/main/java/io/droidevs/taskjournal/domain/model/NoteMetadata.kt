package io.droidevs.taskjournal.domain.model

enum class NotePriority {
    NONE,
    LOW,
    MEDIUM,
    HIGH,
    URGENT
}

enum class JournalMood {
    GREAT,
    GOOD,
    NEUTRAL,
    LOW,
    BAD
}

data class LocationSnapshot(
    val name: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val weatherSummary: String? = null
)

