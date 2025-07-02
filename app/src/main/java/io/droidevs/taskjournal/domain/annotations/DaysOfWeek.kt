package io.droidevs.taskjournal.domain.annotations

import androidx.annotation.IntDef
import io.droidevs.taskjournal.domain.model.Recurrence.Companion.FRIDAY
import io.droidevs.taskjournal.domain.model.Recurrence.Companion.MONDAY
import io.droidevs.taskjournal.domain.model.Recurrence.Companion.SATURDAY
import io.droidevs.taskjournal.domain.model.Recurrence.Companion.SUNDAY
import io.droidevs.taskjournal.domain.model.Recurrence.Companion.THURSDAY
import io.droidevs.taskjournal.domain.model.Recurrence.Companion.TUESDAY
import io.droidevs.taskjournal.domain.model.Recurrence.Companion.WEDNESDAY
import kotlin.intArrayOf

/**
 * Int def for the [byDay] bit field property.
 */
@IntDef(flag = true, value = [SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY])
@Retention(AnnotationRetention.SOURCE)
public annotation class DaysOfWeek