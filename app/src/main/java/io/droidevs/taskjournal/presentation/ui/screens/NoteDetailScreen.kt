package io.droidevs.taskjournal.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import io.droidevs.taskjournal.domain.model.ChecklistItem
import io.droidevs.taskjournal.domain.model.Comment
import io.droidevs.taskjournal.domain.model.NotePriority
import io.droidevs.taskjournal.presentation.core.actions.NoteDetailScreenAction
import io.droidevs.taskjournal.presentation.core.state.NoteDetailScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    state: NoteDetailScreenState,
    onAction: (NoteDetailScreenAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isNewNote) "New note" else "Edit note") },
                navigationIcon = {
                    IconButton(onClick = { onAction(NoteDetailScreenAction.NavigateBack) }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (!state.isNewNote) {
                        IconButton(onClick = { onAction(NoteDetailScreenAction.TogglePin) }) {
                            Icon(
                                imageVector = if (state.isPinned) Icons.Filled.PushPin else Icons.Outlined.PushPin,
                                contentDescription = "Toggle pin"
                            )
                        }
                        if (state.isDeleted) {
                            IconButton(onClick = { onAction(NoteDetailScreenAction.RestoreNote) }) {
                                Icon(Icons.Filled.Restore, contentDescription = "Restore")
                            }
                            IconButton(onClick = { onAction(NoteDetailScreenAction.PermanentlyDeleteNote) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Delete forever")
                            }
                        } else {
                            IconButton(onClick = { onAction(NoteDetailScreenAction.ArchiveNote) }) {
                                Icon(Icons.Filled.Archive, contentDescription = "Archive")
                            }
                            IconButton(onClick = { onAction(NoteDetailScreenAction.DeleteNote) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Delete")
                            }
                        }
                    }
                    IconButton(onClick = { onAction(NoteDetailScreenAction.SaveNote) }) {
                        Icon(Icons.Filled.Check, contentDescription = "Save")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top = padding.calculateTopPadding()),
            contentPadding = PaddingValues(16.dp, 8.dp, 16.dp, 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = state.title,
                    onValueChange = { onAction(NoteDetailScreenAction.TitleChanged(it)) },
                    label = { Text("Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = state.content,
                    onValueChange = { onAction(NoteDetailScreenAction.ContentChanged(it)) },
                    label = { Text("Write something...") },
                    minLines = 5,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PriorityDropdown(
                        selected = state.priority,
                        onSelected = { onAction(NoteDetailScreenAction.PriorityChanged(it)) }
                    )
                    AssistChip(
                        onClick = { onAction(NoteDetailScreenAction.ToggleCompleted) },
                        label = { Text(if (state.isCompleted) "Completed" else "Mark complete") }
                    )
                }
            }

            item {
                Text("Checklist", style = MaterialTheme.typography.titleSmall)
            }
            items(state.checklistItems, key = { "check-${it.id}" }) { item ->
                ChecklistRow(
                    item = item,
                    onToggle = { onAction(NoteDetailScreenAction.ToggleChecklistItem(item.id)) },
                    onDelete = { onAction(NoteDetailScreenAction.DeleteChecklistItem(item.id)) }
                )
            }
            item {
                AddChecklistItemRow(onAdd = { text -> onAction(NoteDetailScreenAction.AddChecklistItem(text)) })
            }

            item { Divider() }

            item {
                Text("Comments", style = MaterialTheme.typography.titleSmall)
            }
            items(state.comments, key = { "comment-${it.id}" }) { comment ->
                CommentRow(
                    comment = comment,
                    onDelete = { onAction(NoteDetailScreenAction.DeleteComment(comment.id)) }
                )
            }
            item {
                AddCommentRow(onAdd = { text -> onAction(NoteDetailScreenAction.AddComment(text)) })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PriorityDropdown(
    selected: NotePriority,
    onSelected: (NotePriority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = selected.name.lowercase().replaceFirstChar { it.uppercase() },
            onValueChange = {},
            readOnly = true,
            label = { Text("Priority") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            NotePriority.entries.forEach { priority ->
                DropdownMenuItem(
                    text = { Text(priority.name.lowercase().replaceFirstChar { it.uppercase() }) },
                    onClick = { onSelected(priority); expanded = false }
                )
            }
        }
    }
}

@Composable
private fun ChecklistRow(
    item: ChecklistItem,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Checkbox(checked = item.isChecked, onCheckedChange = { onToggle() })
            Text(
                item.text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete checklist item")
        }
    }
}

@Composable
private fun AddChecklistItemRow(onAdd: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("Add item") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = {
            if (text.isNotBlank()) {
                onAdd(text)
                text = ""
            }
        }) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }
}

@Composable
private fun CommentRow(comment: Comment, onDelete: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(comment.text, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
        IconButton(onClick = onDelete) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete comment")
        }
    }
}

@Composable
private fun AddCommentRow(onAdd: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("Add a comment") },
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = {
            if (text.isNotBlank()) {
                onAdd(text)
                text = ""
            }
        }) {
            Icon(Icons.Filled.Send, contentDescription = "Send")
        }
    }
}