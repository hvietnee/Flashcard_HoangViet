package com.hoangviet.flashcard

import kotlin.math.max

object SM2Logic {
    // Hàm tính toán lịch ôn tập dựa trên thuật toán SuperMemo-2
    fun calculate(card: Flashcard, quality: Int): Flashcard {
        var repetitions = card.repetition
        var interval = card.interval
        var easeFactor = card.easeFactor

        if (quality >= 3) {
            // Nếu người dùng nhớ thẻ (mức độ >= 3)
            if (repetitions == 0) {
                interval = 1
            } else if (repetitions == 1) {
                interval = 6
            } else {
                interval = (interval * easeFactor).toInt()
            }
            repetitions++
        } else {
            // Nếu người dùng không nhớ thẻ
            repetitions = 0
            interval = 1
        }

        // Cập nhật hệ số độ dễ (Ease Factor)
        easeFactor += (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02)).toFloat()
        if (easeFactor < 1.3f) easeFactor = 1.3f

        // Trả về một bản sao của thẻ với các thông số mới
        return card.copy(
            repetition = repetitions,
            interval = interval,
            easeFactor = easeFactor
        )
    }
}