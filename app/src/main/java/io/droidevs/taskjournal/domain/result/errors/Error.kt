package io.droidevs.taskjournal.domain.result.errors

import io.droidevs.taskjournal.domain.result.RootError


sealed interface Error

data class InternalError(val cause: Throwable) : RootError

data class UnknownError(val cause: Throwable) : RootError