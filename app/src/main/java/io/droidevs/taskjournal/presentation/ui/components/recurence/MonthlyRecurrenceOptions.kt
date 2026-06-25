package io.droidevs.taskjournal.presentation.ui.components.recurence

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.droidevs.taskjournal.domain.model.Recurrence
import io.droidevs.taskjournal.domain.model.Recurrence.Companion.invoke

@Composable
internal fun MonthlyRecurrenceOptions(
    recurrence: Recurrence,
    onRecurrenceChange: (Recurrence) -> Unit
) {
    val isByDayOfMonth = recurrence.byDay == 0

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("On:", style = MaterialTheme.typography.titleSmall)

        // Option 1: On a specific day of the month
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                if (!isByDayOfMonth) {
                    onRecurrenceChange(Recurrence(recurrence) { dayInMonth = 1 })
                }
            }
        ) {
            RadioButton(
                selected = isByDayOfMonth,
                onClick = {
                    if (!isByDayOfMonth) {
                        onRecurrenceChange(Recurrence(recurrence) { dayInMonth = 1 })
                    }
                }
            )
            Text("Day of the month:")
            Spacer(Modifier.width(8.dp))
            OutlinedTextField(
                value = if (isByDayOfMonth) recurrence.byMonthDay.toString() else "",
                onValueChange = { text ->
                    onRecurrenceChange(
                        Recurrence(recurrence) {
                            dayInMonth = text.toIntOrNull()?.coerceIn(-31, 31) ?: 0
                        }
                    )
                },
                enabled = isByDayOfMonth,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(80.dp)
            )
        }

        // Option 2: On the Nth weekday of the month
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                if (isByDayOfMonth) {
                    onRecurrenceChange(Recurrence(recurrence) { setDayOfWeekInMonth(Recurrence.MONDAY, 1) })
                }
            }
        ) {
            RadioButton(
                selected = !isByDayOfMonth,
                onClick = {
                    if (isByDayOfMonth) {
                        onRecurrenceChange(Recurrence(recurrence) { setDayOfWeekInMonth(Recurrence.MONDAY, 1) })
                    }
                }
            )
            Text("The")
            // Dropdowns for week and day would go here
            // For simplicity, this is left as an exercise. A complete implementation would
            // use two ExposedDropdownMenuBox instances similar to the PeriodSelector.
            // When values change, call:
            // onRecurrenceChange(Recurrence(recurrence) { setDayOfWeekInMonth(dayFlag, weekNumber) })
            Text(
                text = if (!isByDayOfMonth) " Nth weekday (e.g. Second Monday)" else "...",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}