package io.droidevs.taskjournal.domain.pager

interface Paginator<key,Item> {
    suspend fun loadNextItems()

    suspend fun reset()
}