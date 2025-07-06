package io.droidevs.taskjournal.presentation.ui.components.recurence

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.droidevs.taskjournal.domain.model.Period

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PeriodSelector(
    selectedPeriod: Period,
    onPeriodSelected: (Period) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val periodNames = Period.entries.associateWith {
        when (it) {
            Period.NONE -> "Does not repeat"
            Period.DAILY -> "Daily"
            Period.WEEKLY -> "Weekly"
            Period.MONTHLY -> "Monthly"
            Period.YEARLY -> "Yearly"
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = periodNames[selectedPeriod] ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Repeats") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Period.entries.forEach { period ->
                DropdownMenuItem(
                    text = { Text(periodNames[period] ?: "") },
                    onClick = {
                        onPeriodSelected(period)
                        expanded = false
                    }
                )
            }
        }
    }
}