package com.shahriyardx.doto.database.todo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    val title: String,
    val description: String,
    val isComplete: Boolean = false,
    val category: String = "Uncategorized",
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) {
    override fun toString(): String {
        return "<Todo id=$id >"
    }
}