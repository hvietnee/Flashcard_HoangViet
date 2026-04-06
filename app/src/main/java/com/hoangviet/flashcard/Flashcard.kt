package com.hoangviet.flashcard

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "flashcards")
data class Flashcard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var front: String,
    var back: String,
    var status: Int = 0,
    var repetition: Int = 0,
    var interval: Int = 0,
    var easeFactor: Double = 2.5,
    var nextReview: Long = System.currentTimeMillis()
) : Serializable