package io.droidevs.taskjournal.domain.dispatchers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

suspend fun <T> withIO(dispatchers: AppDispatchersProvider,block: suspend CoroutineScope.() -> T, ): T {
    return withContext(dispatchers.io, block)
}

suspend fun <T> withMain(dispatchers: AppDispatchersProvider,block: suspend CoroutineScope.() -> T, ): T {
    return withContext(dispatchers.main, block)
}


fun lunchIo(dispatchers: AppDispatchersProvider,runner: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(dispatchers.io).launch { runner.invoke((this)) }
fun lunchMain(dispatchers: AppDispatchersProvider,runner: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(dispatchers.main).launch { runner.invoke((this)) }

suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
    map { async { f(it) } }.awaitAll()
}