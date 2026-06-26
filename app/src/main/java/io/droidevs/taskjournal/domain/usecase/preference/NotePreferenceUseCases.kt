package io.droidevs.taskjournal.domain.usecase.preference

import io.droidevs.taskjournal.domain.model.NotePriority
import io.droidevs.taskjournal.domain.model.SortOrder
import io.droidevs.taskjournal.domain.preference.AutoSaveDraftsPreference
import io.droidevs.taskjournal.domain.preference.CompactModePreference
import io.droidevs.taskjournal.domain.preference.ConfirmBeforeDeletePreference
import io.droidevs.taskjournal.domain.preference.DefaultCategoryPreference
import io.droidevs.taskjournal.domain.preference.DefaultPriorityPreference
import io.droidevs.taskjournal.domain.preference.KeepDeletedDaysPreference
import io.droidevs.taskjournal.domain.preference.ShowArchivedPreference
import io.droidevs.taskjournal.domain.preference.ShowCompletedPreference
import io.droidevs.taskjournal.domain.preference.ShowPinnedFirstPreference
import io.droidevs.taskjournal.domain.preference.ShowWordCountPreference
import io.droidevs.taskjournal.domain.preference.SortOrderPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSortOrderUseCase @Inject constructor(private val pref: SortOrderPreference) {
    operator fun invoke(): Flow<Result<SortOrder, PreferenceError>> = pref.value
}

class SetSortOrderUseCase @Inject constructor(private val pref: SortOrderPreference) {
    suspend operator fun invoke(order: SortOrder): Result<SortOrder, PreferenceError> = pref.save(order)
}

class GetShowCompletedUseCase @Inject constructor(private val pref: ShowCompletedPreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetShowCompletedUseCase @Inject constructor(private val pref: ShowCompletedPreference) {
    suspend operator fun invoke(show: Boolean): Result<Boolean, PreferenceError> = pref.save(show)
}

class GetShowArchivedUseCase @Inject constructor(private val pref: ShowArchivedPreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetShowArchivedUseCase @Inject constructor(private val pref: ShowArchivedPreference) {
    suspend operator fun invoke(show: Boolean): Result<Boolean, PreferenceError> = pref.save(show)
}

class GetShowPinnedFirstUseCase @Inject constructor(private val pref: ShowPinnedFirstPreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetShowPinnedFirstUseCase @Inject constructor(private val pref: ShowPinnedFirstPreference) {
    suspend operator fun invoke(show: Boolean): Result<Boolean, PreferenceError> = pref.save(show)
}

class GetCompactModeUseCase @Inject constructor(private val pref: CompactModePreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetCompactModeUseCase @Inject constructor(private val pref: CompactModePreference) {
    suspend operator fun invoke(enabled: Boolean): Result<Boolean, PreferenceError> = pref.save(enabled)
}

class GetShowWordCountUseCase @Inject constructor(private val pref: ShowWordCountPreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetShowWordCountUseCase @Inject constructor(private val pref: ShowWordCountPreference) {
    suspend operator fun invoke(show: Boolean): Result<Boolean, PreferenceError> = pref.save(show)
}

class GetAutoSaveDraftsUseCase @Inject constructor(private val pref: AutoSaveDraftsPreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetAutoSaveDraftsUseCase @Inject constructor(private val pref: AutoSaveDraftsPreference) {
    suspend operator fun invoke(enabled: Boolean): Result<Boolean, PreferenceError> = pref.save(enabled)
}

class GetConfirmBeforeDeleteUseCase @Inject constructor(private val pref: ConfirmBeforeDeletePreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetConfirmBeforeDeleteUseCase @Inject constructor(private val pref: ConfirmBeforeDeletePreference) {
    suspend operator fun invoke(enabled: Boolean): Result<Boolean, PreferenceError> = pref.save(enabled)
}

class GetKeepDeletedDaysUseCase @Inject constructor(private val pref: KeepDeletedDaysPreference) {
    operator fun invoke(): Flow<Result<Int, PreferenceError>> = pref.value
}

class SetKeepDeletedDaysUseCase @Inject constructor(private val pref: KeepDeletedDaysPreference) {
    suspend operator fun invoke(days: Int): Result<Int, PreferenceError> = pref.save(days.coerceAtLeast(0))
}

class GetDefaultCategoryUseCase @Inject constructor(private val pref: DefaultCategoryPreference) {
    operator fun invoke(): Flow<Result<Long?, PreferenceError>> = pref.value
}

class SetDefaultCategoryUseCase @Inject constructor(private val pref: DefaultCategoryPreference) {
    suspend operator fun invoke(categoryId: Long?): Result<Long?, PreferenceError> = pref.save(categoryId)
}

class GetDefaultPriorityUseCase @Inject constructor(private val pref: DefaultPriorityPreference) {
    operator fun invoke(): Flow<Result<NotePriority, PreferenceError>> = pref.value
}

class SetDefaultPriorityUseCase @Inject constructor(private val pref: DefaultPriorityPreference) {
    suspend operator fun invoke(priority: NotePriority): Result<NotePriority, PreferenceError> = pref.save(priority)
}

/**
 * Aggregate of note-list/editor display and behavior preference use cases.
 */
data class NotePreferencesUseCases @Inject constructor(
    val getSortOrder: GetSortOrderUseCase,
    val setSortOrder: SetSortOrderUseCase,
    val getShowCompleted: GetShowCompletedUseCase,
    val setShowCompleted: SetShowCompletedUseCase,
    val getShowArchived: GetShowArchivedUseCase,
    val setShowArchived: SetShowArchivedUseCase,
    val getShowPinnedFirst: GetShowPinnedFirstUseCase,
    val setShowPinnedFirst: SetShowPinnedFirstUseCase,
    val getCompactMode: GetCompactModeUseCase,
    val setCompactMode: SetCompactModeUseCase,
    val getShowWordCount: GetShowWordCountUseCase,
    val setShowWordCount: SetShowWordCountUseCase,
    val getAutoSaveDrafts: GetAutoSaveDraftsUseCase,
    val setAutoSaveDrafts: SetAutoSaveDraftsUseCase,
    val getConfirmBeforeDelete: GetConfirmBeforeDeleteUseCase,
    val setConfirmBeforeDelete: SetConfirmBeforeDeleteUseCase,
    val getKeepDeletedDays: GetKeepDeletedDaysUseCase,
    val setKeepDeletedDays: SetKeepDeletedDaysUseCase,
    val getDefaultCategory: GetDefaultCategoryUseCase,
    val setDefaultCategory: SetDefaultCategoryUseCase,
    val getDefaultPriority: GetDefaultPriorityUseCase,
    val setDefaultPriority: SetDefaultPriorityUseCase
)