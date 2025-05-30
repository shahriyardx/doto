package com.shahriyardx.doto

import kotlinx.serialization.Serializable

@Serializable
object AddTodoScreen

@Serializable
object TodosScreen

@Serializable
data class DetailsScreen(
    val id: Int
)
