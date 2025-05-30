package com.shahriyardx.doto.di

import androidx.room.Room
import com.shahriyardx.doto.database.Database
import com.shahriyardx.doto.database.migrations.todoCategoryMigration
import com.shahriyardx.doto.repositories.TodoRepository
import com.shahriyardx.doto.repositories.TodoRepositoryImpl
import com.shahriyardx.doto.viewmodels.todo.TodoViewModel
import com.shahriyardx.doto.viewmodels.todo_details.TodoDetailsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(), Database::class.java, "database.db"
        ).addMigrations(todoCategoryMigration).build()
    }

//    single {
//        get<Database>().dao
//    } // in case only dao is required

    single<TodoRepository> {
        TodoRepositoryImpl(get())
    }

    viewModel {
        TodoViewModel(get())
    }

    viewModel { (todoId: Int) ->
        TodoDetailsViewModel(todoId, get())
    }
}

