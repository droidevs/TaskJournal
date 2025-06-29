package io.droidevs.taskjournal.domain.model

sealed class OrderType {
    data object Ascending: OrderType()
    data object Descending: OrderType()
}