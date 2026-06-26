package io.droidevs.taskjournal.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.droidevs.taskjournal.presentation.core.actions.HomeScreenAction
import io.droidevs.taskjournal.presentation.core.state.HomeScreenState
import io.droidevs.taskjournal.presentation.models.NoteUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeScreenState,
    onAction: (HomeScreenAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Journal") },
                actions = {
                    IconButton(onClick = { onAction(HomeScreenAction.NavigateToCategories) }) {
                        Icon(Icons.Filled.Folder, contentDescription = "Categories")
                    }
                    IconButton(onClick = { onAction(HomeScreenAction.NavigateToTrash) }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Trash")
                    }
                    IconButton(onClick = { onAction(HomeScreenAction.NavigateToSettings) }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction(HomeScreenAction.CreateNote) }) {
                Icon(Icons.Filled.Add, contentDescription = "New note")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(padding.calculateTopPadding(), 16.dp, 16.dp, 96.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.pinnedNotes.isNotEmpty()) {
                item {
                    Text("Pinned", style = MaterialTheme.typography.titleMedium)
                }
                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(state.pinnedNotes, key = { it.id }) { note ->
                            NoteSummaryCard(note = note, onClick = { onAction(HomeScreenAction.OpenNote(note.id)) })
                        }
                    }
                }
            }

            if (state.dueSoonNotes.isNotEmpty()) {
                item {
                    Text("Due soon", style = MaterialTheme.typography.titleMedium)
                }
                items(state.dueSoonNotes, key = { "due-${it.id}" }) { note ->
                    NoteRow(note = note, onClick = { onAction(HomeScreenAction.OpenNote(note.id)) })
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Recent notes", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "See all",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                    )
                }
            }
            items(state.recentNotes, key = { "recent-${it.id}" }) { note ->
                NoteRow(note = note, onClick = { onAction(HomeScreenAction.OpenNote(note.id)) })
            }

            if (!state.isLoading && state.recentNotes.isEmpty() && state.pinnedNotes.isEmpty()) {
                item {
                    Text(
                        "No notes yet. Tap + to create your first one.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun NoteSummaryCard(note: NoteUi, onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(2.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(12.dp).fillMaxWidth()) {
            Text(note.title, style = MaterialTheme.typography.titleSmall, maxLines = 1)
            Text(
                note.preview,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun NoteRow(note: NoteUi, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(note.title, style = MaterialTheme.typography.titleSmall, maxLines = 1)
            if (note.preview.isNotBlank()) {
                Text(
                    note.preview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}