package io.droidevs.taskjournal.domain.model

data class Attachment(
    val id: Long = 0,
    val noteId: Long = 0,
    val type: AttachmentType = AttachmentType.IMAGE,
    val path: String = "",
    val fileName: String = "",
    val description: String = "",
    val mimeType: String? = null,
    val sizeBytes: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)

enum class AttachmentType {
    AUDIO,
    IMAGE,
    VIDEO,
    GENERIC,
    FILE
}

