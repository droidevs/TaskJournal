package io.droidevs.taskjournal.platform

import android.content.Context
import androidx.biometric.BiometricManager
import dagger.hilt.android.qualifiers.ApplicationContext
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.BiometricAuthError
import io.droidevs.taskjournal.domain.services.BiometricService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class BiometricServiceImpl @Inject constructor(
    @ApplicationContext appContext: Context
) : BiometricService {

    private val biometricManager = BiometricManager.from(appContext)


    override fun canAuthenticate(): Boolean {
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                true
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                false
            }

            else -> {
                false
            }
        }
    }

    override fun authenticate(): Flow<Result<Unit, BiometricAuthError>> {
        return flowOf(Result.Failure(BiometricAuthError.Error))
    }
}
