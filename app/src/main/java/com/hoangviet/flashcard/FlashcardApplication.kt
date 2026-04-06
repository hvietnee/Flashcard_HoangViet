package com.hoangviet.flashcard

import android.app.Application

class FlashcardApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { FlashcardRepository(database.flashcardDao()) }
}