package com.hoangviet.flashcard

import java.util.*

class SM2Logic {

    fun updateFlashcard(card: Flashcard, quality: Int): Flashcard {
        var n = card.level
        var ef = card.easeFactor
        var interval = card.interval

        val newEf = ef + (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02))
        ef = if (newEf < 1.3) 1.3 else newEf

        if (quality >= 3) {
            when (n) {
                0 -> interval = 1
                1 -> interval = 6
                else -> interval = (interval * ef).toInt()
            }
            n += 1
        } else {
            n = 0
            interval = 1
        }

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, interval)
        val nextDate = calendar.timeInMillis

        return card.copy(
            level = n,
            easeFactor = ef,
            interval = interval,
            nextDate = nextDate
        )
    }
}