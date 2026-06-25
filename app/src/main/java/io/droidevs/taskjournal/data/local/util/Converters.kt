package io.droidevs.taskjournal.data.local.util

import androidx.room.TypeConverter
import io.droidevs.taskjournal.domain.model.AttachmentType
import io.droidevs.taskjournal.domain.model.JournalMood
import io.droidevs.taskjournal.domain.model.NotePriority
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun notePriorityToString(value: NotePriority?): String? = value?.name

    @TypeConverter
    fun stringToNotePriority(value: String?): NotePriority? {
        return value?.let { runCatching { NotePriority.valueOf(it) }.getOrDefault(NotePriority.NONE) }
    }

    @TypeConverter
    fun journalMoodToString(value: JournalMood?): String? = value?.name

    @TypeConverter
    fun stringToJournalMood(value: String?): JournalMood? {
        return value?.let { runCatching { JournalMood.valueOf(it) }.getOrNull() }
    }

    @TypeConverter
    fun attachmentTypeToString(value: AttachmentType?): String? = value?.name

    @TypeConverter
    fun stringToAttachmentType(value: String?): AttachmentType? {
        return value?.let { runCatching { AttachmentType.valueOf(it) }.getOrDefault(AttachmentType.GENERIC) }
    }
} 
