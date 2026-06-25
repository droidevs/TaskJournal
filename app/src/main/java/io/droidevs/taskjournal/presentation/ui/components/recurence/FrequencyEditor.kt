package io.droidevs.taskjournal.presentation.ui.components.recurence

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.droidevs.taskjournal.domain.model.Period
import io.droidevs.taskjournal.domain.model.Recurrence

@Composable
internal fun FrequencyEditor(
    recurrence: Recurrence,
    onFrequencyChange: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Every", style = MaterialTheme.typography.bodyLarge)
        OutlinedTextField(
            value = recurrence.frequency.toString(),
            onValueChange = { text ->
                onFrequencyChange(text.toIntOrNull() ?: 1)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(80.dp)
        )
        val periodName = when (recurrence.period) {
            Period.DAILY -> "day"
            Period.WEEKLY -> "week"
            Period.MONTHLY -> "month"
            Period.YEARLY -> "year"
            else -> ""
        }
        Text(
            if (recurrence.frequency > 1) "${periodName}s" else periodName,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}