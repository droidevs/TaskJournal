package io.droidevs.taskjournal.domain.model

enum class Period {
    /**
     * Period for a recurrence that does not repeat.
     * Finding events of this recurrence will return none.
     */
    NONE,

    /**
     * Period for a recurrence that occurs every X day(s).
     */
    DAILY,

    /**
     * Period for a recurrence that occurs every X week(s),
     * on one or several days of the week.
     */
    WEEKLY,

    /**
     * Period for a recurrence that occurs every X month(s), on a particular day of the month.
     */
    MONTHLY,

    /**
     * Period for a recurrence that occurs every X year(s).
     */
    YEARLY
}