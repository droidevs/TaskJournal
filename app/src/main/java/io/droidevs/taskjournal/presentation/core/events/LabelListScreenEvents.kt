package io.droidevs.taskjournal.presentation.core.events

sealed interface LabelListScreenEvent {

    data object NavigateBack : LabelListScreenEvent

    data object LabelCreatedSuccessfully : LabelListScreenEvent

    data object LabelCreateFailed : LabelListScreenEvent

    data object LabelRenamedSuccessfully : LabelListScreenEvent

    data object LabelRenameFailed : LabelListScreenEvent

    data object LabelDeletedSuccessfully : LabelListScreenEvent

    data object LabelDeleteFailed : LabelListScreenEvent
}