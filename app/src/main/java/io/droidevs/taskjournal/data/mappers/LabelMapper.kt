package io.droidevs.taskjournal.data.mappers

import io.droidevs.taskjournal.data.local.entity.LabelEntity
import io.droidevs.taskjournal.domain.model.Label

fun LabelEntity.toDomain(): Label = Label(
    id = id,
    name = name,
    color = color,
    hidden = hidden,
    createdAt = createdAt
)

fun Label.toEntity(): LabelEntity = LabelEntity(
    id = id,
    name = name,
    color = color,
    hidden = hidden,
    createdAt = createdAt
)

