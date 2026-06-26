package io.droidevs.taskjournal.presentation.models.mappers

import io.droidevs.taskjournal.domain.model.Note
import io.droidevs.taskjournal.presentation.models.NoteUi
import okhttp3.internal.toHexString
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val relativeDateFormatter = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())

/**
 * Maps a domain [Note] (plus its already-resolved category, if any) to a [NoteUi]
 * ready for display in list/grid screens.
 *
 * Note: category name/color must be resolved by the caller (e.g. via a join in the
 * repository or a lookup map in the ViewModel) since [Note] itself only stores a
 * `categoryId` foreign key.
 */
@OptIn(ExperimentalStdlibApi::class)
fun Note.toUi(categoryName: String? = null, categoryColorHex: String? = null): NoteUi {
    val plainTextContent = content.replace(Regex("[#*_`>\\-]"), "").trim()
    return NoteUi(
        id = id,
        title = title.ifBlank { "Untitled" },
        preview = plainTextContent.take(140),
        categoryName = category?.name?: categoryName,
        categoryColorHex = category?.color?.toHexString(format = HexFormat.Default)?: categoryColorHex,
        priority = priority,
        isPinned = isPinned,
        isCompleted = isCompleted,
        isArchived = isArchived,
        dueAtLabel = dueAt?.let { relativeDateFormatter.format(Date(it)) },
        updatedAtLabel = relativeDateFormatter.format(Date(updatedAt)),
        wordCount = plainTextContent.split(Regex("\\s+")).count { it.isNotBlank() }
    )
}