package io.droidevs.taskjournal.domain.model

enum class EndType {
    /** Recurrence will never end. */
    NEVER,

    /** Recurrence will end on a date. */
    BY_DATE,

    /** Recurrence will end after a number of events. */
    BY_COUNT
}