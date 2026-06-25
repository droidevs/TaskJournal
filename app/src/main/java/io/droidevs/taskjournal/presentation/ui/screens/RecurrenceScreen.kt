package io.droidevs.taskjournal.presentation.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.droidevs.taskjournal.domain.model.Recurrence
import io.droidevs.taskjournal.presentation.ui.components.recurence.RecurrencePicker

// Assume your Recurrence class and its enums/constants are in this scope
// e.g., import com.yourpackage.Recurrence
// import com.yourpackage.Recurrence.Period.*
// ... etc.

/**
 * A full-screen example demonstrating how to use the RecurrencePicker.
 * It holds the state and provides the callbacks for date/time pickers.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecurrenceScreen() {
    var recurrence by remember { mutableStateOf(Recurrence.DOES_NOT_REPEAT) }

    // --- STATE FOR YOUR PICKERS ---
    // You would manage the visibility of your chosen date picker dialog here
    val showDatePicker = remember { mutableStateOf(false) }

    // When your date picker returns a date, update the recurrence
    if (showDatePicker.value) {
        /*
         * LAUNCH YOUR DATE PICKER DIALOG HERE
         *
         * Example with a hypothetical DatePickerDialog:
         *
         * MyDatePickerDialog(
         *   onDateSelected = { selectedDateInMillis ->
         *     recurrence = Recurrence(recurrence) {
         *       endDate = selectedDateInMillis
         *     }
         *     showDatePicker.value = false
         *   },
         *   onDismiss = { showDatePicker.value = false }
         * )
         */
        // For demonstration, we just log and close it.
        println("Pretend to show date picker...")
        // In a real app, you'd call a function that shows your library's picker.
        // On selection, you update the recurrence state as shown in the comment block above.
        showDatePicker.value = false // Close the dialog state
    }


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Set Recurrence") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            RecurrencePicker(
                recurrence = recurrence,
                onRecurrenceChange = { newRecurrence ->
                    recurrence = newRecurrence
                },
                onLaunchDatePicker = {
                    showDatePicker.value = true
                }
            )

            // Display the current recurrence for debugging
            Spacer(Modifier.height(24.dp))
            Text("Current Recurrence State:", style = MaterialTheme.typography.titleMedium)
            Text(recurrence.toString(), style = MaterialTheme.typography.bodySmall)
        }
    }
}






