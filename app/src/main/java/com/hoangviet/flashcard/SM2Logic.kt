package com.hoangviet.flashcard

object SM2Logic {
    // Bản DEMO: Tính toán thời gian theo Phút để thầy xem ngay tại chỗ
    fun updateCardForDemo(card: Flashcard, status: Int): Flashcard {
        val currentTime = System.currentTimeMillis()

        if (status == 1) { // MÀU CAM: Chưa biết
            card.status = 1
            // Lên lịch học lại sau đúng 1 phút (60.000 mili giây)
            card.nextReview = currentTime + (1 * 60 * 1000L)
        } else { // MÀU XANH: Đã biết
            card.status = 2
            // Lên lịch học lại sau 2 phút (120.000 mili giây)
            card.nextReview = currentTime + (2 * 60 * 1000L)
        }
        return card
    }
}