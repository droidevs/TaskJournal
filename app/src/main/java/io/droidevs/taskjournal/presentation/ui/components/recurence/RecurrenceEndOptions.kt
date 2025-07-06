package io.droidevs.taskjournal.presentation.ui.components.recurence

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.droidevs.taskjournal.domain.model.EndType
import io.droidevs.taskjournal.domain.model.Recurrence
import io.droidevs.taskjournal.domain.model.Recurrence.Companion.invoke
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
internal fun RecurrenceEndOptions(
    recurrence: Recurrence,
    onRecurrenceChange: (Recurrence) -> Unit,
    onLaunchDatePicker: () -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("MMM d, yyyy", Locale.getDefault()) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Ends:", style = MaterialTheme.typography.titleSmall)

        // Option 1: Never
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onRecurrenceChange(Recurrence(recurrence) { endType = EndType.NEVER }) }
        ) {
            RadioButton(
                selected = recurrence.endType == EndType.NEVER,
                onClick = { onRecurrenceChange(Recurrence(recurrence) { endType = EndType.NEVER }) }
            )
            Text("Never")
        }

        // Option 2: On a date
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = recurrence.endType == EndType.BY_DATE,
                onClick = {
                    // Set a default end date if switching to this option
                    if (recurrence.endDate == Recurrence.DATE_NONE) {
                        onRecurrenceChange(Recurrence(recurrence) { endDate = System.currentTimeMillis() })
                    } else {
                        onRecurrenceChange(Recurrence(recurrence) { endType = EndType.BY_DATE })
                    }
                }
            )
            Text("On")
            Spacer(Modifier.width(8.dp))
            OutlinedButton(
                onClick = onLaunchDatePicker,
                enabled = recurrence.endType == EndType.BY_DATE
            ) {
                val dateText = if (recurrence.endDate != Recurrence.DATE_NONE) {
                    dateFormat.format(Date(recurrence.endDate))
                } else {
                    "Select a date"
                }
                Text(dateText)
            }
        }

        // Option 3: After a number of occurrences
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = recurrence.endType == EndType.BY_COUNT,
                onClick = { onRecurrenceChange(Recurrence(recurrence) { endCount = 10 }) }
            )
            Text("After")
            Spacer(Modifier.width(8.dp))
            OutlinedTextField(
                value = if (recurrence.endType == EndType.BY_COUNT) recurrence.endCount.toString() else "",
                onValueChange = { text ->
                    onRecurrenceChange(Recurrence(recurrence) { endCount = text.toIntOrNull() ?: 1 })
                },
                enabled = recurrence.endType == EndType.BY_COUNT,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(80.dp)
            )
            Text("events")
        }
    }
}