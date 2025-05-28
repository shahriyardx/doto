package com.shahriyardx.doto.database.todo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    val title: String,
    val description: String,
    var isComplete: Boolean = false,
    val isArchived: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) {
    override fun toString(): String {
        return "<Todo id=$id >"
    }
}