package io.droidevs.taskjournal.domain.result.errors

import io.droidevs.onlinelibrary.domain.result.RootError

sealed interface Error


data class InternalError(val message: String) : RootError

data class UnknownError(val message: String) : RootError