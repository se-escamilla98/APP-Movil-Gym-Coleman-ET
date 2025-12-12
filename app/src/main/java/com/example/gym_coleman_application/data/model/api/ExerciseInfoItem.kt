package com.example.gym_coleman_application.data.model.api

data class ExerciseInfoItem(
    val id: Int,
    val name: String,
    val description: String?,
    val category: Category?,
    val muscles: List<Muscle>?,
    val muscles_secondary: List<Muscle>?,
    val images: List<ExerciseImage>?
)

data class ExerciseBase(
    val id: Int,
    val name: String
)

data class Category(
    val id: Int,
    val name: String
)

data class Muscle(
    val id: Int,
    val name: String
)

data class ExerciseImage(
    val id: Int,
    val image: String
)
