package io.droidevs.taskjournal.domain.pager

import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DataError
import io.droidevs.taskjournal.domain.result.onFailureSuspend
import io.droidevs.taskjournal.domain.result.onSuccessSuspend

open class DefaultPaginator<Key,Item>(
    val initialKey : Key,
    private val onLoadUpdated : (Boolean) -> Unit,
    private val onRequest : suspend (nextKey : Key) -> Result<List<Item>, DataError>,
    private val getNextKey : suspend (key: Key, items : List<Item>) -> Key,
    private val onError : suspend (error : DataError) -> Unit,
    private val onSuccess : (items : List<Item> , newKey : Key) -> Unit
) : Paginator<Key, Item> {


    private var currentKey = initialKey
    private var isMakingRequest = false



    override suspend fun loadNextItems() {
        if (isMakingRequest)
            return

        isMakingRequest = true
        onLoadUpdated(true)

        val result = onRequest(currentKey)
        isMakingRequest = false

        result.onSuccessSuspend {
            currentKey = getNextKey(currentKey, it)
            onSuccess(it,currentKey)
            onLoadUpdated(false)
        }
        result.onFailureSuspend {
            onError(it)
            onLoadUpdated(false)
            return@onFailureSuspend
        }
    }

    override suspend fun reset() {
        currentKey = initialKey
    }

}