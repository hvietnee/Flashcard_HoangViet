package com.hoangviet.flashcard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hoangviet.flashcard.databinding.ActivityLearningBinding
import kotlinx.coroutines.launch

class LearningActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLearningBinding
    private var flashcards: List<Flashcard> = listOf()
    private var currentIndex = 0
    private var isFront = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val deckId = intent.getLongExtra("DECK_ID", -1)

        val database = AppDatabase.getDatabase(this)
        lifecycleScope.launch {
            // Lấy danh sách thẻ từ Dao
            flashcards = database.flashcardDao().getFlashcardsForDeck(deckId)
            if (flashcards.isNotEmpty()) {
                showCard()
            }
        }

        // Nhấn vào thẻ để lật
        binding.cardContainer.setOnClickListener {
            isFront = !isFront
            showCard()
        }

        // Nút chọn mức độ
        binding.btnEasy.setOnClickListener { handleAnswer(5) }
        binding.btnHard.setOnClickListener { handleAnswer(1) }
    }

    private fun showCard() {
        if (flashcards.isEmpty()) return
        val currentCard = flashcards[currentIndex]
        // Sửa lỗi front/back ở đây cho Việt
        binding.tvContent.text = if (isFront) currentCard.front else currentCard.back
        binding.tvHint.text = if (isFront) "Chạm để xem đáp án" else "Bạn thấy từ này thế nào?"
    }

    private fun handleAnswer(quality: Int) {
        val currentCard = flashcards[currentIndex]
        val updatedCard = SM2Logic.calculate(currentCard, quality)

        lifecycleScope.launch {
            AppDatabase.getDatabase(this@LearningActivity).flashcardDao().updateFlashcard(updatedCard)

            if (currentIndex < flashcards.size - 1) {
                currentIndex++
                isFront = true
                showCard()
            } else {
                finish()
            }
        }
    }
}