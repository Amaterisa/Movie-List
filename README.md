# Movie List App

Welcome to Movie List App, a sleek and modern Android application where you can explore, search, and manage your favorite movies with ease. Leveraging Clean Architecture principles, the app ensures scalability, maintainability, and clear separation of concerns.


## Features

- Browse movies by genre.
- Stay updated with trending movies in the entertainment world.
- Save movies to your watchlist, mark them as watched, or remove them.
- Find your favorite films or explore hidden gems by searching titles directly.


## Tech Stack

**Programming Language:** Kotlin

**Architecture:** MVVM (Model-View-ViewModel) with Clean Architecture

**Dependency Injection:** Hilt

**Networking:** Retrofit for REST API communication

**Database:** Room for local persistence

**Concurrency:** Kotlin Coroutines for managing background tasks

**Flow:** For reactive and asynchronous stream handling


## Project Structure

- **movielistapp/**
    - **data/**: Manages data fetching and storage, integrating both local and remote sources.
    - **di/**: Dependency injection.
    - **domain/**: Houses core business logic with repository interfaces, use cases, and models.
    - **presentation/**: Handles UI logic with Android components (Activities, Fragments) and MVVM pattern (ViewModels).