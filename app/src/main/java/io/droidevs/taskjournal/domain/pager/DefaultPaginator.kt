package io.droidevs.counterapp.domain.pager

import io.droidevs.bmicalc.domain.pager.Paginator
import io.droidevs.counterapp.domain.result.Result
import io.droidevs.counterapp.domain.result.errors.DatabaseError
import io.droidevs.counterapp.domain.result.errors.Error
import io.droidevs.counterapp.domain.result.onFailureSuspend
import io.droidevs.counterapp.domain.result.onSuccessSuspend
import io.droidevs.taskjournal.domain.pager.PaginationState

abstract class DefaultPaginator<Key,Item>(
    val initialKey : Key,
    private  val onLoadUpdated : (Boolean) -> Unit,
    private val onRequest : suspend (nextKey : Key) -> Result<List<Item>, DatabaseError>,
    private val getNextKey : suspend (items : List<Item>) -> Key,
    private val onError : suspend (error : Error) -> Unit,
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
            currentKey = getNextKey(items)

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

        isMakingRequest = true

        loadNextPage()

        isMakingRequest = false
    }

}