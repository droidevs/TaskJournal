package io.droidevs.taskjournal.presentation.navigation

import io.droidevs.taskjournal.presentation.navigation.roots.Screen

/**
 * Thin abstraction over [androidx.navigation.NavHostController] so that screen builders
 * and ViewModels never depend on Compose Navigation APIs directly. Makes it possible to
 * fake navigation in tests and keeps builder files declarative.
 */
interface Navigator {

    /** Navigate to [screen], adding it to the back stack. */
    fun navigateTo(screen: Screen)

    /**
     * Navigate to [screen], clearing everything up to and including [popUpToScreen]
     * (or the whole stack to the start destination if null), and avoiding duplicate
     * destinations on the stack if [launchSingleTop] is true.
     */
    fun navigateTo(
        screen: Screen,
        popUpToScreen: Screen? = null,
        inclusive: Boolean = false,
        launchSingleTop: Boolean = true
    )

    /** Pop the current destination off the back stack. Returns false if there was nothing to pop. */
    fun navigateBack(): Boolean

    /** Pop the back stack until [screen] is reached. */
    fun navigateBackTo(screen: Screen, inclusive: Boolean = false)
}