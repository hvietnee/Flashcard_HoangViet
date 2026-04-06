package com.hoangviet.flashcard

import kotlinx.coroutines.flow.Flow

class FlashcardRepository(val dao: FlashcardDao) { // Đặt là 'val' để MainActivity truy cập được
    val allFlashcards: Flow<List<Flashcard>> = dao.getAllFlashcards()

    suspend fun insert(flashcard: Flashcard) {
        dao.insert(flashcard)
    }
}