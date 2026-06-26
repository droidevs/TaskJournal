package io.droidevs.taskjournal.presentation.navigation.builders

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.droidevs.taskjournal.presentation.core.events.NoteListScreenEvent
import io.droidevs.taskjournal.presentation.navigation.Navigator
import io.droidevs.taskjournal.presentation.navigation.roots.Screen
import io.droidevs.taskjournal.presentation.ui.screens.NoteListScreen
import io.droidevs.taskjournal.presentation.ui.snackbar.SnackBarAction
import io.droidevs.taskjournal.presentation.ui.snackbar.SnackBarController
import io.droidevs.taskjournal.presentation.ui.snackbar.SnackBarEvent
import io.droidevs.taskjournal.presentation.utils.ObserveAsEvents
import io.droidevs.taskjournal.presentation.viewmodels.NoteListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun NavGraphBuilder.noteListScreen(
    navigator: Navigator,
    scope: CoroutineScope
) {
    composable<Screen.NoteList> {

        val viewModel: NoteListViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()

        ObserveAsEvents(flow = viewModel.events) { event ->
            when (event) {
                is NoteListScreenEvent.NavigateToNoteDetail -> {
                    navigator.navigateTo(Screen.NoteEditExisting(event.noteId))
                }
                NoteListScreenEvent.NavigateToNoteCreate -> {
                    navigator.navigateTo(Screen.NoteEditNew)
                }
                NoteListScreenEvent.NavigateToCategoryList -> {
                    navigator.navigateTo(Screen.CategoryList)
                }
                NoteListScreenEvent.NavigateToTrash -> {
                    navigator.navigateTo(Screen.Trash)
                }
                NoteListScreenEvent.NavigateToSettings -> {
                    navigator.navigateTo(Screen.Settings)
                }
                NoteListScreenEvent.NoteTrashedSuccessfully -> {
                    scope.launch {
                        SnackBarController.sendEvent(SnackBarEvent(message = "Note moved to trash"))
                    }
                }
                NoteListScreenEvent.NotesTrashedSuccessfully -> {
                    scope.launch {
                        SnackBarController.sendEvent(SnackBarEvent(message = "Notes moved to trash"))
                    }
                }
                NoteListScreenEvent.NoteTrashFailed, NoteListScreenEvent.NoteActionFailed -> {
                    scope.launch {
                        SnackBarController.sendEvent(
                            SnackBarEvent(
                                message = "Something went wrong, please try again.",
                                action = SnackBarAction(
                                    name = "Retry",
                                    onAction = { viewModel.onAction(io.droidevs.taskjournal.presentation.core.actions.NoteListScreenAction.RefreshNotes) }
                                )
                            )
                        )
                    }
                }
            }
        }

        NoteListScreen(
            state = state,
            onAction = viewModel::onAction
        )
    }
}