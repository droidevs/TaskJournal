package io.droidevs.taskjournal.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.droidevs.taskjournal.domain.model.ChecklistItem
import io.droidevs.taskjournal.domain.model.Comment
import io.droidevs.taskjournal.domain.model.Note
import io.droidevs.taskjournal.domain.result.onFailureSuspend
import io.droidevs.taskjournal.domain.result.onSuccessSuspend
import io.droidevs.taskjournal.domain.usecase.ChecklistItemUseCases
import io.droidevs.taskjournal.domain.usecase.CommentUseCases
import io.droidevs.taskjournal.domain.usecase.LabelUseCases
import io.droidevs.taskjournal.domain.usecase.NotesUseCases
import io.droidevs.taskjournal.presentation.core.actions.NoteDetailScreenAction
import io.droidevs.taskjournal.presentation.core.events.NoteDetailScreenEvent
import io.droidevs.taskjournal.presentation.core.state.NoteDetailScreenState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Handles both note creation and note editing. `noteId` arrives via [SavedStateHandle]
 * and is `null`/absent for the "create new note" flow.
 */
@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteUseCases: NotesUseCases,
    private val checklistUseCases: ChecklistItemUseCases,
    private val commentUseCases: CommentUseCases,
    private val labelUseCases: LabelUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteId: Long? = savedStateHandle.get<Long>("noteId")?.takeIf { it > 0 }

    private val _state = MutableStateFlow(
        NoteDetailScreenState(noteId = noteId, isNewNote = noteId == null)
    )
    val state: StateFlow<NoteDetailScreenState> = _state.asStateFlow()
        .onStart { if (noteId != null) loadNote(noteId) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NoteDetailScreenState(noteId = noteId, isNewNote = noteId == null)
        )

    private val _events = MutableSharedFlow<NoteDetailScreenEvent>()
    val events: SharedFlow<NoteDetailScreenEvent> = _events.asSharedFlow()

    private fun loadNote(id: Long) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            noteUseCases.getById(id).collect { result ->
                result.onSuccessSuspend { note ->
                    if (note != null) {
                        _state.update {
                            it.copy(
                                title = note.title,
                                content = note.content,
                                category = note.category,
                                labels = note.labels,
                                priority = note.priority,
                                mood = note.mood,
                                dueAt = note.dueAt,
                                reminderAt = note.reminderAt,
                                isPinned = note.isPinned,
                                isCompleted = note.isCompleted,
                                isArchived = note.isArchived,
                                isDeleted = note.isDeleted,
                                attachments = note.attachments,
                                checklistItems = note.checklistItems,
                                comments = note.comments,
                                reminders = note.reminders,
                                isLoading = false
                            )
                        }
                    } else {
                        _state.update { it.copy(isLoading = false) }
                    }
                }.onFailureSuspend { error ->
                    _state.update { it.copy(error = error, isLoading = false) }
                }
            }
        }
    }

    private fun buildNoteFromState(): Note {
        val current = _state.value
        val now = System.currentTimeMillis()
        return Note(
            id = current.noteId ?: 0,
            title = current.title,
            content = current.content,
            createdAt = now,
            updatedAt = now,
            isDeleted = current.isDeleted,
            isPinned = current.isPinned,
            isArchived = current.isArchived,
            isCompleted = current.isCompleted,
            priority = current.priority,
            dueAt = current.dueAt,
            reminderAt = current.reminderAt,
            mood = current.mood,
            category = current.category
        )
    }

    private fun saveNote() {
        val current = _state.value
        if (current.title.isBlank()) {
            viewModelScope.launch { _events.emit(NoteDetailScreenEvent.ValidationTitleRequired) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            val note = buildNoteFromState()

            if (current.isNewNote) {
                noteUseCases.add(note)
                    .onSuccessSuspend { newId ->
                        if (current.labels.isNotEmpty()) {
                            labelUseCases.setForNote(newId, current.labels.map { it.id })
                        }
                        _state.update { it.copy(isSaving = false, noteId = newId, isNewNote = false) }
                        _events.emit(NoteDetailScreenEvent.NoteSavedSuccessfully)
                        _events.emit(NoteDetailScreenEvent.NavigateBack)
                    }
                    .onFailureSuspend {
                        _state.update { it.copy(isSaving = false) }
                        _events.emit(NoteDetailScreenEvent.NoteSaveFailed)
                    }
            } else {
                noteUseCases.update(note)
                    .onSuccessSuspend {
                        val id = current.noteId
                        if (id != null) {
                            labelUseCases.setForNote(id, current.labels.map { it.id })
                        }
                        _state.update { it.copy(isSaving = false) }
                        _events.emit(NoteDetailScreenEvent.NoteSavedSuccessfully)
                        _events.emit(NoteDetailScreenEvent.NavigateBack)
                    }
                    .onFailureSuspend {
                        _state.update { it.copy(isSaving = false) }
                        _events.emit(NoteDetailScreenEvent.NoteSaveFailed)
                    }
            }
        }
    }

    private fun togglePin() {
        val noteId = _state.value.noteId ?: return
        viewModelScope.launch {
            noteUseCases.getById(noteId).first()
                .onSuccessSuspend { note ->
                    if (note != null) {
                        noteUseCases.togglePin(note)
                            .onSuccessSuspend { _state.update { it.copy(isPinned = !it.isPinned) } }
                            .onFailureSuspend { _events.emit(NoteDetailScreenEvent.NoteActionFailed) }
                    }
                }
                .onFailureSuspend { _events.emit(NoteDetailScreenEvent.NoteActionFailed) }
        }
    }

    private fun toggleCompleted() {
        val noteId = _state.value.noteId ?: return
        viewModelScope.launch {
            noteUseCases.getById(noteId).first()
                .onSuccessSuspend { note ->
                    if (note != null) {
                        noteUseCases.toggleCompleted(note)
                            .onSuccessSuspend { _state.update { it.copy(isCompleted = !it.isCompleted) } }
                            .onFailureSuspend { _events.emit(NoteDetailScreenEvent.NoteActionFailed) }
                    }
                }
                .onFailureSuspend { _events.emit(NoteDetailScreenEvent.NoteActionFailed) }
        }
    }

    private fun archiveNote() {
        viewModelScope.launch {
            val note = buildNoteFromState()
            noteUseCases.archive(note)
                .onSuccessSuspend {
                    _state.update { it.copy(isArchived = true) }
                    _events.emit(NoteDetailScreenEvent.NoteArchivedSuccessfully)
                    _events.emit(NoteDetailScreenEvent.NavigateBack)
                }
                .onFailureSuspend { _events.emit(NoteDetailScreenEvent.NoteActionFailed) }
        }
    }

    private fun deleteNote() {
        viewModelScope.launch {
            val note = buildNoteFromState()
            noteUseCases.trash(note)
                .onSuccessSuspend {
                    _events.emit(NoteDetailScreenEvent.NoteDeletedSuccessfully)
                    _events.emit(NoteDetailScreenEvent.NavigateBack)
                }
                .onFailureSuspend { _events.emit(NoteDetailScreenEvent.NoteDeleteFailed) }
        }
    }

    private fun restoreNote() {
        viewModelScope.launch {
            val note = buildNoteFromState()
            noteUseCases.restore(note)
                .onSuccessSuspend {
                    _state.update { it.copy(isDeleted = false) }
                    _events.emit(NoteDetailScreenEvent.NoteRestoredSuccessfully)
                }
                .onFailureSuspend { _events.emit(NoteDetailScreenEvent.NoteActionFailed) }
        }
    }

    private fun permanentlyDeleteNote() {
        val id = _state.value.noteId ?: return
        viewModelScope.launch {
            noteUseCases.permanentlyDelete(id)
                .onSuccessSuspend {
                    _events.emit(NoteDetailScreenEvent.NoteDeletedSuccessfully)
                    _events.emit(NoteDetailScreenEvent.NavigateBack)
                }
                .onFailureSuspend { _events.emit(NoteDetailScreenEvent.NoteDeleteFailed) }
        }
    }

    private fun addComment(text: String) {
        val noteId = _state.value.noteId
        if (text.isBlank() || noteId == null) return
        viewModelScope.launch {
            commentUseCases.insert(Comment(noteId = noteId, text = text.trim()))
                .onSuccessSuspend { loadNote(noteId) }
                .onFailureSuspend { _events.emit(NoteDetailScreenEvent.NoteActionFailed) }
        }
    }

    private fun deleteComment(commentId: Long) {
        val noteId = _state.value.noteId ?: return
        viewModelScope.launch {
            commentUseCases.delete(commentId)
                .onSuccessSuspend {
                    _state.update { current -> current.copy(comments = current.comments.filterNot { it.id == commentId }) }
                }
                .onFailureSuspend { _events.emit(NoteDetailScreenEvent.NoteActionFailed) }
        }
    }

    private fun addChecklistItem(text: String) {
        val noteId = _state.value.noteId
        if (text.isBlank() || noteId == null) return
        viewModelScope.launch {
            val nextPosition = _state.value.checklistItems.size
            checklistUseCases.insert(ChecklistItem(noteId = noteId, text = text.trim(), position = nextPosition))
                .onSuccessSuspend { loadNote(noteId) }
                .onFailureSuspend { _events.emit(NoteDetailScreenEvent.NoteActionFailed) }
        }
    }

    private fun toggleChecklistItem(itemId: Long) {
        val item = _state.value.checklistItems.find { it.id == itemId } ?: return
        viewModelScope.launch {
            checklistUseCases.toggle(item)
                .onSuccessSuspend {
                    _state.update { current ->
                        current.copy(checklistItems = current.checklistItems.map {
                            if (it.id == itemId) it.copy(isChecked = !it.isChecked) else it
                        })
                    }
                }
                .onFailureSuspend { _events.emit(NoteDetailScreenEvent.NoteActionFailed) }
        }
    }

    private fun deleteChecklistItem(itemId: Long) {
        viewModelScope.launch {
            checklistUseCases.delete(itemId)
                .onSuccessSuspend {
                    _state.update { current -> current.copy(checklistItems = current.checklistItems.filterNot { it.id == itemId }) }
                }
                .onFailureSuspend { _events.emit(NoteDetailScreenEvent.NoteActionFailed) }
        }
    }

    fun onAction(action: NoteDetailScreenAction) {
        when (action) {
            is NoteDetailScreenAction.TitleChanged -> {
                _state.update { it.copy(title = action.title, isDirty = true) }
            }
            is NoteDetailScreenAction.ContentChanged -> {
                _state.update { it.copy(content = action.content, isDirty = true) }
            }
            is NoteDetailScreenAction.CategoryChanged -> {
                // Category resolution (id -> full Category) happens where the picker is shown;
                // here we just clear it when null since we don't have a CategoryRepository lookup.
                if (action.categoryId == null) _state.update { it.copy(category = null, isDirty = true) }
            }
            is NoteDetailScreenAction.PriorityChanged -> {
                _state.update { it.copy(priority = action.priority, isDirty = true) }
            }
            is NoteDetailScreenAction.MoodChanged -> {
                _state.update { it.copy(mood = action.mood, isDirty = true) }
            }
            is NoteDetailScreenAction.DueDateChanged -> {
                _state.update { it.copy(dueAt = action.dueAt, isDirty = true) }
            }
            is NoteDetailScreenAction.ReminderChanged -> {
                _state.update { it.copy(reminderAt = action.reminderAt, isDirty = true) }
            }
            is NoteDetailScreenAction.LabelsChanged -> {
                viewModelScope.launch {
                    val resolved = action.labelIds.mapNotNull { id ->
                        var found: io.droidevs.taskjournal.domain.model.Label? = null
                        labelUseCases.getById(id).first().onSuccessSuspend { found = it }
                        found
                    }
                    _state.update { it.copy(labels = resolved, isDirty = true) }
                }
            }
            NoteDetailScreenAction.TogglePin -> togglePin()
            NoteDetailScreenAction.ToggleCompleted -> toggleCompleted()
            NoteDetailScreenAction.SaveNote -> saveNote()
            NoteDetailScreenAction.ArchiveNote -> archiveNote()
            NoteDetailScreenAction.DeleteNote -> deleteNote()
            NoteDetailScreenAction.RestoreNote -> restoreNote()
            NoteDetailScreenAction.PermanentlyDeleteNote -> permanentlyDeleteNote()
            is NoteDetailScreenAction.AddComment -> addComment(action.text)
            is NoteDetailScreenAction.DeleteComment -> deleteComment(action.commentId)
            is NoteDetailScreenAction.AddChecklistItem -> addChecklistItem(action.text)
            is NoteDetailScreenAction.ToggleChecklistItem -> toggleChecklistItem(action.itemId)
            is NoteDetailScreenAction.DeleteChecklistItem -> deleteChecklistItem(action.itemId)
            NoteDetailScreenAction.NavigateBack -> {
                viewModelScope.launch { _events.emit(NoteDetailScreenEvent.NavigateBack) }
            }
        }
    }
}