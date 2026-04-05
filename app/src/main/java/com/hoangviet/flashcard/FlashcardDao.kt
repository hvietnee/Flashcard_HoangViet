package com.hoangviet.flashcard

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {

    @Query("SELECT * FROM flashcards WHERE nextDate <= :currentTime ORDER BY id DESC")
    fun getDueFlashcards(currentTime: Long): Flow<List<Flashcard>>

    @Query("SELECT * FROM flashcards ORDER BY id DESC")
    fun getAllFlashcards(): Flow<List<Flashcard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(flashcard: Flashcard)

    @Delete
    suspend fun delete(flashcard: Flashcard)

    @Query("DELETE FROM flashcards")
    suspend fun deleteAll()
}