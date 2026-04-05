package com.hoangviet.flashcard

import androidx.room.Entity
import androidx.room.PrimaryKey

// Gắn nhãn Entity để tạo bảng "deck_table" trong máy
@Entity(tableName = "deck_table")
data class Deck(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,        // Tên bộ thẻ (VD: Từ vựng IT)
    val description: String  // Mô tả ngắn gọn
)