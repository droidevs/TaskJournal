package io.droidevs.taskjournal.domain.pager

import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import io.droidevs.taskjournal.domain.result.onFailureSuspend
import io.droidevs.taskjournal.domain.result.onSuccessSuspend

class DefaultPaginator<Key, Item>(
    val initialKey : Key,
    private  val onLoadUpdated : (Boolean) -> Unit,
    private val onRequest : suspend (nextKey : Key) -> Result<List<Item>, DatabaseError>,
    private val getNextKey : suspend (key: Key, items : List<Item>) -> Key,
    private val onError : suspend (error : DatabaseError) -> Unit,
    private val onSuccess : (items : List<Item> , newKey : Key) -> Unit
) : Paginator<Key, Item> {


    private var currentKey = initialKey
    private var isMakingRequest = false



    override suspend fun loadNextPage() {
        if (isMakingRequest)
            return

        isMakingRequest = true
        onLoadUpdated(true)

        val result = onRequest(currentKey)
        isMakingRequest = false

        result.onSuccessSuspend { items ->
            currentKey = getNextKey(currentKey, items)

            onSuccess(items,currentKey)

            onLoadUpdated(false)
        }.onFailureSuspend {
            onError(it)
            onLoadUpdated(false)
            return@onFailureSuspend
        }
    }

    override suspend fun refresh() {
        if (isMakingRequest) return
        currentKey = initialKey

        loadNextPage()
    }

    suspend fun loadNextItems() = loadNextPage()

    suspend fun reset() = refresh()
}
