package io.droidevs.taskjournal.presentation.navigation.builders

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.droidevs.taskjournal.presentation.core.events.HomeScreenEvent
import io.droidevs.taskjournal.presentation.navigation.Navigator
import io.droidevs.taskjournal.presentation.navigation.roots.Screen
import io.droidevs.taskjournal.presentation.ui.layouts.SomethingWrongLayout
import io.droidevs.taskjournal.presentation.ui.screens.HomeScreen
import io.droidevs.taskjournal.presentation.ui.snackbar.SnackBarController
import io.droidevs.taskjournal.presentation.ui.snackbar.SnackBarEvent
import io.droidevs.taskjournal.presentation.utils.ObserveAsEvents
import io.droidevs.taskjournal.presentation.viewmodels.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun NavGraphBuilder.homeScreen(
    navigator: Navigator,
    scope: CoroutineScope
) {
    composable<Screen.Home> {

        val viewModel: HomeViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()

        ObserveAsEvents(flow = viewModel.events) { event ->
            when (event) {
                is HomeScreenEvent.NavigateToNoteDetail -> {
                    navigator.navigateTo(Screen.NoteEditExisting(event.noteId))
                }
                HomeScreenEvent.NavigateToNoteCreate -> {
                    navigator.navigateTo(Screen.NoteEditNew)
                }
                HomeScreenEvent.NavigateToNoteList -> {
                    navigator.navigateTo(Screen.NoteList)
                }
                HomeScreenEvent.NavigateToCategoryList -> {
                    navigator.navigateTo(Screen.CategoryList)
                }
                HomeScreenEvent.NavigateToTrash -> {
                    navigator.navigateTo(Screen.Trash)
                }
                HomeScreenEvent.NavigateToSettings -> {
                    navigator.navigateTo(Screen.Settings)
                }
                HomeScreenEvent.HomeActionFailed -> {
                    scope.launch {
                        SnackBarController.sendEvent(
                            SnackBarEvent(message = "Something went wrong, please try again.")
                        )
                    }
                }
            }
        }

        if (state.error != null) {
            SomethingWrongLayout(
                onRetry = { viewModel.onAction(io.droidevs.taskjournal.presentation.core.actions.HomeScreenAction.Refresh) }
            )
        } else {
            HomeScreen(
                state = state,
                onAction = viewModel::onAction
            )
        }
    }
}