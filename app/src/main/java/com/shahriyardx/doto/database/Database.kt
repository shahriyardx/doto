package com.shahriyardx.doto.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shahriyardx.doto.database.todo.TodoDao
import com.shahriyardx.doto.database.todo.TodoEntity

@Database(
    entities = [TodoEntity::class],
    version = 2,
)
abstract class Database: RoomDatabase() {
    abstract val dao: TodoDao
}