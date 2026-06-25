package io.droidevs.taskjournal.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class NoteWithDetails(
    @Embedded
    val note: NoteEntity,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: CategoryEntity?,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = LabelRef::class,
            parentColumn = "noteId",
            entityColumn = "labelId"
        )
    )
    val labels: List<LabelEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "note_id"
    )
    val attachments: List<AttachmentEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "note_id"
    )
    val checklistItems: List<ChecklistItemEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "note_id"
    )
    val comments: List<CommentEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "note_id"
    )
    val reminders: List<ReminderEntity>
)

