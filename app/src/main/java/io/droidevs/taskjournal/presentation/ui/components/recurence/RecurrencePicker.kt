package io.droidevs.taskjournal.presentation.ui.components.recurence

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.droidevs.taskjournal.domain.model.Period
import io.droidevs.taskjournal.domain.model.Recurrence

import kotlin.collections.mapNotNull


/**
 * A self-contained Composable for selecting a recurrence rule.
 *
 * @param recurrence The current recurrence state.
 * @param onRecurrenceChange A callback invoked with a new Recurrence object when any setting is changed.
 * @param onLaunchDatePicker A callback to request the hosting composable to show a date picker.
 */
@Composable
fun RecurrencePicker(
    recurrence: Recurrence,
    onRecurrenceChange: (Recurrence) -> Unit,
    onLaunchDatePicker: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // Period Selector (e.g., Daily, Weekly, Monthly)
        PeriodSelector(
            selectedPeriod = recurrence.period,
            onPeriodSelected = { newPeriod ->
                // When period changes, create a fresh builder for that period
                // to reset incompatible properties (like byDay from a weekly rule)
                onRecurrenceChange(Recurrence(newPeriod) {})
            }
        )

        // Don't show other options if it doesn't repeat
        if (recurrence.period != Period.NONE) {
            Divider()

            // Frequency selector (e.g., "Repeats every 2 weeks")
            FrequencyEditor(
                recurrence = recurrence,
                onFrequencyChange = { newFrequency ->
                    onRecurrenceChange(Recurrence(recurrence) { frequency = newFrequency })
                }
            )

            // Period-specific options
            when (recurrence.period) {
                Period.WEEKLY -> {
                    WeeklyRecurrenceOptions(
                        recurrence = recurrence,
                        onDaysChange = { newByDay ->
                            onRecurrenceChange(
                                // The setDaysOfWeek builder method expects an array of flags
                                Recurrence(recurrence) {
                                    // Create a list of day flags that are set in the new bitmask
                                    val dayFlags = (Recurrence.SUNDAY..Recurrence.SATURDAY).mapNotNull { flag ->
                                        if ((newByDay and flag) == flag) flag else null
                                    }
                                    setDaysOfWeek(*dayFlags.toIntArray())
                                }
                            )
                        }
                    )
                }
                Period.MONTHLY -> {
                    MonthlyRecurrenceOptions(
                        recurrence = recurrence,
                        onRecurrenceChange = onRecurrenceChange
                    )
                }
                else -> {
                    // No extra options for Daily or Yearly in this UI
                }
            }

            Divider()

            // End condition (e.g., Never, On a date, After X events)
            RecurrenceEndOptions(
                recurrence = recurrence,
                onRecurrenceChange = onRecurrenceChange,
                onLaunchDatePicker = onLaunchDatePicker
            )
        }
    }
}