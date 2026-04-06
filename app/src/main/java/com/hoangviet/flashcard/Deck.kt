package com.hoangviet.flashcard

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deck_table")
data class Deck(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val description: String
)