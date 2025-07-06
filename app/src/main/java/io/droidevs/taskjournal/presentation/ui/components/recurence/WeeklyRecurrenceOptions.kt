package io.droidevs.taskjournal.presentation.ui.components.recurence

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.droidevs.taskjournal.domain.model.Recurrence

@Composable
internal fun WeeklyRecurrenceOptions(
    recurrence: Recurrence,
    onDaysChange: (Int) -> Unit
) {
    val days = remember {
        listOf(
            "S" to Recurrence.SUNDAY,
            "M" to Recurrence.MONDAY,
            "T" to Recurrence.TUESDAY,
            "W" to Recurrence.WEDNESDAY,
            "T" to Recurrence.THURSDAY,
            "F" to Recurrence.FRIDAY,
            "S" to Recurrence.SATURDAY
        )
    }

    Column {
        Text("On days:", style = MaterialTheme.typography.titleSmall)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            days.forEach { (label, dayFlag) ->
                val isSelected = recurrence.isRecurringOnDaysOfWeek(dayFlag)
                FilledTonalIconToggleButton(
                    checked = isSelected,
                    onCheckedChange = {
                        val newByDay = if (it) {
                            recurrence.byDay or dayFlag
                        } else {
                            recurrence.byDay and dayFlag.inv()
                        }
                        onDaysChange(newByDay)
                    }
                ) {
                    Text(label)
                }
            }
        }
    }
}