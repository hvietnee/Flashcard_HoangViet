package com.hoangviet.flashcard

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcards")
data class Flashcard(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val deckId: Long,
    val front: String,
    val back: String,

    // ĐÂY LÀ 3 CÁI "CÔNG TƠ MÉT" ÔNG ĐANG THIẾU:
    val repetition: Int = 0,      // Số lần đã ôn tập
    val interval: Int = 1,        // Khoảng cách ngày ôn tập tiếp theo
    val easeFactor: Float = 2.5f  // Độ dễ của thẻ (mặc định là 2.5)
)