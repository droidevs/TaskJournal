package io.droidevs.taskjournal.presentation.navigation

import androidx.navigation.NavHostController
import io.droidevs.taskjournal.presentation.navigation.roots.Screen

/**
 * Default [Navigator] implementation. Construct one per [NavHostController] instance
 * (typically remembered at the NavGraph composable's root) and pass it down to every
 * screen builder.
 */
class DefaultNavigator(
    private val navController: NavHostController
) : Navigator {

    override fun navigateTo(screen: Screen) {
        navController.navigate(screen)
    }

    override fun navigateTo(
        screen: Screen,
        popUpToScreen: Screen?,
        inclusive: Boolean,
        launchSingleTop: Boolean
    ) {
        navController.navigate(screen) {
            this.launchSingleTop = launchSingleTop
            if (popUpToScreen != null) {
                popUpTo(popUpToScreen) {
                    this.inclusive = inclusive
                }
            }
        }
    }

    override fun navigateBack(): Boolean = navController.popBackStack()

    override fun navigateBackTo(screen: Screen, inclusive: Boolean) {
        navController.popBackStack(screen, inclusive)
    }
}