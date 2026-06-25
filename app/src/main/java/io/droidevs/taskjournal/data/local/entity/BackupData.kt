package io.droidevs.taskjournal.data.local.entity

data class BackupData(
    val notes: List<NoteEntity>,
    val categories: List<CategoryEntity>,
    val labels: List<LabelEntity> = emptyList(),
    val labelRefs: List<LabelRef> = emptyList(),
    val attachments: List<AttachmentEntity> = emptyList(),
    val checklistItems: List<ChecklistItemEntity> = emptyList(),
    val comments: List<CommentEntity> = emptyList(),
    val reminders: List<ReminderEntity> = emptyList()
)
