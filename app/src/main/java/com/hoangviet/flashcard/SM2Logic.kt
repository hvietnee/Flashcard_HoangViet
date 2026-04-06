package com.hoangviet.flashcard

object SM2Logic {

    fun updateCardForDemo(card: Flashcard, status: Int): Flashcard {
        val currentTime = System.currentTimeMillis()

        if (status == 1) {
            card.status = 1

            card.nextReview = currentTime + (1 * 60 * 1000L)
        } else {
            card.status = 2

            card.nextReview = currentTime + (2 * 60 * 1000L)
        }
        return card
    }
}