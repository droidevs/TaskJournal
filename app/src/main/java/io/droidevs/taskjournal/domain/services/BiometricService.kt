package io.droidevs.taskjournal.domain.services

import io.droidevs.taskjournal.domain.result.errors.BiometricAuthError
import io.droidevs.taskjournal.domain.result.Result
import kotlinx.coroutines.flow.Flow

interface BiometricService {

    fun canAuthenticate() : Boolean


    fun authenticate() : Flow<Result<Unit, BiometricAuthError>>

}