package io.droidevs.taskjournal.data.local.exceptions


sealed class DatabaseException : Exception() {

    class NoElementFound : DatabaseException()
    class ConstraintViolated : DatabaseException()
}
