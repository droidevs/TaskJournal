package io.droidevs.taskjournal.domain.usecase.category

data class CategoryUseCases(
    val getById : GetCategoryByIdUseCase,
    val getAll : GetAllCategoriesUseCase,
    val update : UpdateCategoryUseCase,
    val delete : DeleteCategoryUseCase,
    val add : InsertCategoryUseCase
)