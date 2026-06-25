package io.droidevs.taskjournal.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object NoteList : Screen("notes")
    object NoteDetail : Screen("note/{noteId}") {
        fun createRoute(noteId: Long) = "note/$noteId"
    }
    object CategoryList : Screen("categories")
    object CategoryDetail : Screen("category/{categoryId}") {
        fun createRoute(categoryId: Long) = "category/$categoryId"
    }
    object Settings : Screen("settings")
} 