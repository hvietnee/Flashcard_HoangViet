package com.hoangviet.flashcard

import kotlinx.coroutines.flow.Flow

class FlashcardRepository(val dao: FlashcardDao) {
    val allFlashcards: Flow<List<Flashcard>> = dao.getAllFlashcards()

    suspend fun insert(flashcard: Flashcard) {
        dao.insert(flashcard)
    }
}