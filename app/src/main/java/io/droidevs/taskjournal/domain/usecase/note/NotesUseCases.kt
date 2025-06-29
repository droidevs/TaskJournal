package io.droidevs.taskjournal.domain.usecase.note

class NotesUseCases(
    val add : InsertNoteUseCase,
    val delete : DeleteNoteUseCase,
    val update : UpdateNoteUseCase,
    val get : GetAllNotesUseCase,
    val search : SearchNotesUseCase,
    val getDeleted : GetDeletedNotesUseCase,
    val getPinned: GetPinnedNotesUseCase
)