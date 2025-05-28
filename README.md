# DoTo - Android To-Do List Application

DoTo is a modern Android application designed to help users manage their tasks efficiently. It leverages Jetpack Compose for the UI, Room for local data persistence, and Kotlin Coroutines with Flow for asynchronous operations, following MVVM architecture.

## Features

*   Add, view, edit, and delete tasks.
*   Mark tasks as completed.
*   Filter tasks (e.g., All, Active, Completed).
*   Persistent storage of tasks using Room database.
*   Modern, reactive UI built with Jetpack Compose.

## Tech Stack & Architecture

*   **UI:** Jetpack Compose (Declarative UI toolkit)
*   **Architecture:** MVVM (Model-View-ViewModel)
    *   **ViewModel:** `androidx.lifecycle.ViewModel` - Manages UI-related data in a lifecycle-conscious way.
    *   **Repository (implied):** Likely used to abstract data sources (Room database).
    *   **Model:** Room Entities (`TodoEntity`).
*   **Data Persistence:** Room Persistence Library (`androidx.room`) - Local SQLite database.
*   **Asynchronous Programming:** Kotlin Coroutines & Flow - For managing background tasks and reactive data streams.
*   **Build System:** Gradle with Kotlin DSL (`build.gradle.kts`)
*   **Annotation Processing:** KSP (Kotlin Symbol Processing) - Used by Room for code generation.
*   **Language:** Kotlin

## Key Components & Tricky Parts

### 1. State Management with ViewModel and Compose

*   **`TodoViewModel.kt`**: This is central to the MVVM architecture.
    *   It holds the UI state (e.g., list of todos, current filter type) typically in a `StateFlow` or `MutableStateFlow`.
    *   Exposes events/actions (e.g., `onEvent(TodoAction.Filter(filterType))`) that UI components can trigger.
    *   Interacts with the Repository to fetch or modify data.
*   **Collecting State in Composables**: