package io.droidevs.taskjournal.domain.result.errors

import io.droidevs.taskjournal.domain.result.RootError

sealed class BiometricAuthError : RootError {

    data object Cancelled : BiometricAuthError()

    data object Error : BiometricAuthError()

}