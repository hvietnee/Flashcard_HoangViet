package com.hoangviet.flashcard

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcards")
// Phải có chữ "data" thì mới dùng được hàm .copy() trong thuật toán SM-2 nhé Việt
data class Flashcard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val deckId: Int, // Để biết thẻ này thuộc bộ nào
    val front: String,
    val back: String,
    val level: Int = 0,           // Số lần học thành công
    val interval: Int = 0,        // Khoảng cách ngày học tiếp theo
    val easeFactor: Double = 2.5, // Độ dễ (mặc định của SM-2 là 2.5)
    val nextDate: Long = System.currentTimeMillis() // Ngày học tiếp theo (dạng milis)
)