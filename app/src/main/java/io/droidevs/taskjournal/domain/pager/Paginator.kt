package io.droidevs.taskjournal.domain.pager

interface Paginator<Key,Item> {

    suspend fun loadNextPage()

    suspend fun refresh()
}
