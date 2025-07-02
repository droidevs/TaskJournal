package io.droidevs.taskjournal.data.helpers

import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import io.droidevs.taskjournal.domain.result.Result
suspend fun <T> withRoomTimeout(
    timeoutMs: Long,
    block: suspend () -> T
): Result<T, DatabaseError> {
    return try {
        withTimeout(timeoutMs) {
            Result.Success(block())
        }
    } catch (e: TimeoutCancellationException) {
        Result.Failure(DatabaseError.TimeOut(timeoutMs))
    } catch (e: Exception) {
        throw e
    }
}