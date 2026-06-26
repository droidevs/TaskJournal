package io.droidevs.taskjournal.presentation.navigation.roots

import kotlinx.serialization.Serializable

/**
 * Type-safe navigation destinations (Navigation-Compose type-safe routes).
 * Each object/class here is a destination; classes carry their navigation args.
 *
 * Note: requires the `androidx.navigation:navigation-compose` version that supports
 * `@Serializable` routes (2.8+) and the `kotlinx-serialization` plugin. If staying on
 * navigation-compose 2.7.7 (current libs.versions.toml), see the string-route fallback
 * comment at the bottom of this file.
 */
sealed interface Screen {

    @Serializable
    data object Home : Screen

    @Serializable
    data object NoteList : Screen

    @Serializable
    data class NoteDetail(val noteId: Long) : Screen

    @Serializable
    data object NoteEdit : Screen {
        // Use NoteEditNew / NoteEditExisting below instead of this marker directly.
    }

    @Serializable
    data object NoteEditNew : Screen

    @Serializable
    data class NoteEditExisting(val noteId: Long) : Screen

    @Serializable
    data object CategoryList : Screen

    @Serializable
    data class CategoryDetail(val categoryId: Long) : Screen

    @Serializable
    data object CategoryEditNew : Screen

    @Serializable
    data class CategoryEditExisting(val categoryId: Long) : Screen

    @Serializable
    data object LabelList : Screen

    @Serializable
    data object Trash : Screen

    @Serializable
    data object Settings : Screen

    @Serializable
    data object RecurrencePicker : Screen
}