package io.droidevs.taskjournal.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * A data class that represents the relationship between a Note and its Category.
 * Room uses this to fetch a Note and its corresponding Category in one go.
 */
data class NoteWithCategory(
    // @Embedded tells Room to treat the fields of NoteEntity as if they were
    // fields of this NoteWithCategory class.
    @Embedded
    val note: NoteEntity,

    // @Relation defines the relationship.
    // parentColumn = the column in the parent entity (NoteEntity) that holds the foreign key.
    // entityColumn = the column in the child entity (CategoryEntity) that the key references.
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    // The category can be null because 'categoryId' in NoteEntity is nullable,
    // and the foreign key is set to SET_NULL on delete.
    val category: CategoryEntity?
)