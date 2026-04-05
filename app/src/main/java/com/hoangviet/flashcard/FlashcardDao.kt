package com.hoangviet.flashcard

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {
    // Lệnh lấy toàn bộ thẻ (để hết đỏ getAllFlashcards)
    @Query("SELECT * FROM flashcards")
    fun getAllFlashcards(): Flow<List<Flashcard>>

    // Lệnh lấy thẻ theo bộ (để học tập)
    @Query("SELECT * FROM flashcards WHERE deckId = :deckId")
    suspend fun getFlashcardsForDeck(deckId: Long): List<Flashcard>

    @Insert
    suspend fun insert(flashcard: Flashcard)

    @Update
    suspend fun updateFlashcard(flashcard: Flashcard)

    // Lệnh xóa (để hết đỏ delete)
    @Delete
    suspend fun delete(flashcard: Flashcard)
}