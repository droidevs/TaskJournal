package io.droidevs.taskjournal.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.droidevs.taskjournal.presentation.core.actions.NoteListScreenAction
import io.droidevs.taskjournal.presentation.core.state.NoteListScreenState
import io.droidevs.taskjournal.presentation.models.NoteUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    state: NoteListScreenState,
    onAction: (NoteListScreenAction) -> Unit
) {
    var showSearch by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (showSearch) {
                        OutlinedTextField(
                            value = state.searchQuery,
                            onValueChange = { onAction(NoteListScreenAction.Search(it)) },
                            placeholder = { Text("Search notes") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else if (state.hasSelection) {
                        Text("${state.selectedNoteIds.size} selected")
                    } else {
                        Text("Notes")
                    }
                },
                navigationIcon = {
                    if (state.hasSelection) {
                        IconButton(onClick = { onAction(NoteListScreenAction.ClearSelection) }) {
                            Icon(Icons.Filled.Close, contentDescription = "Clear selection")
                        }
                    }
                },
                actions = {
                    if (state.hasSelection) {
                        IconButton(onClick = { onAction(NoteListScreenAction.TrashSelectedNotes) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Delete selected")
                        }
                    } else {
                        IconButton(onClick = { showSearch = !showSearch }) {
                            Icon(Icons.Filled.Search, contentDescription = "Search")
                        }
                        NoteListOverflowMenu(onAction = onAction)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction(NoteListScreenAction.CreateNote) }) {
                Icon(Icons.Filled.Add, contentDescription = "New note")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(top = padding.calculateTopPadding())) {
            if (state.notes.isEmpty() && !state.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (state.searchQuery.isBlank()) {
                            "No notes yet. Tap + to add one."
                        } else {
                            "No notes match \"${state.searchQuery}\"."
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp, 8.dp, 16.dp, 96.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.notes, key = { it.id }) { note ->
                        NoteListItem(
                            note = note,
                            isSelected = note.id in state.selectedNoteIds,
                            hasSelection = state.hasSelection,
                            onClick = {
                                if (state.hasSelection) {
                                    if (note.id in state.selectedNoteIds) {
                                        onAction(NoteListScreenAction.DeselectNote(note.id))
                                    } else {
                                        onAction(NoteListScreenAction.SelectNote(note.id))
                                    }
                                } else {
                                    onAction(NoteListScreenAction.OpenNote(note.id))
                                }
                            },
                            onLongClick = { onAction(NoteListScreenAction.SelectNote(note.id)) },
                            onTogglePin = { onAction(NoteListScreenAction.TogglePin(note.id)) },
                            onToggleCompleted = { onAction(NoteListScreenAction.ToggleCompleted(note.id)) }
                        )
                    }
                    if (state.isLoading) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            }
                        }
                    } else if (!state.endReached) {
                        item {
                            androidx.compose.runtime.LaunchedEffect(state.notes.size) {
                                onAction(NoteListScreenAction.LoadMoreNotes)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NoteListOverflowMenu(onAction: (NoteListScreenAction) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Filled.MoreVert, contentDescription = "More options")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("Categories") },
                onClick = { expanded = false; onAction(NoteListScreenAction.NavigateToCategories) }
            )
            DropdownMenuItem(
                text = { Text("Trash") },
                onClick = { expanded = false; onAction(NoteListScreenAction.NavigateToTrash) }
            )
            DropdownMenuItem(
                text = { Text("Settings") },
                onClick = { expanded = false; onAction(NoteListScreenAction.NavigateToSettings) }
            )
        }
    }
}

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
private fun NoteListItem(
    note: NoteUi,
    isSelected: Boolean,
    hasSelection: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onTogglePin: () -> Unit,
    onToggleCompleted: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (note.isCompleted) 0.6f else 1f)
            .then(
                Modifier.combinedClickableCompat(
                    onClick = onClick,
                    onLongClick = onLongClick
                )
            )
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            IconButton(onClick = onToggleCompleted, modifier = Modifier.size(36.dp)) {
                Icon(
                    imageVector = if (note.isCompleted) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                    contentDescription = "Toggle completed",
                    tint = if (note.isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(modifier = Modifier.weight(1f).padding(start = 4.dp)) {
                Text(
                    note.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (note.preview.isNotBlank()) {
                    Text(
                        note.preview,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    note.categoryName?.let {
                        Text(it, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    }
                    note.dueAtLabel?.let {
                        Text("Due $it", style = MaterialTheme.typography.labelSmall)
                    }
                    Text(note.updatedAtLabel, style = MaterialTheme.typography.labelSmall)
                }
            }

            if (!hasSelection) {
                IconButton(onClick = onTogglePin, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = if (note.isPinned) Icons.Filled.PushPin else Icons.Outlined.PushPin,
                        contentDescription = "Toggle pin",
                        tint = if (note.isPinned) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}