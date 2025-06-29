package io.droidevs.taskjournal.domain.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface AppDispatchersProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}