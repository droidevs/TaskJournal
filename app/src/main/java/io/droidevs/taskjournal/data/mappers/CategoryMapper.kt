package io.droidevs.taskjournal.data.mappers

import io.droidevs.taskjournal.data.local.entity.CategoryEntity
import io.droidevs.taskjournal.domain.model.Category

fun CategoryEntity.toDomainModel(): Category {
    return Category(
        id = id,
        name = name,
        description = description,
        color = color
    )
}


fun Category.toEntity() : CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        description = description,
        color = color
    )
}