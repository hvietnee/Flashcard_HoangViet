package com.hoangviet.flashcard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hoangviet.flashcard.databinding.ActivityLearningBinding
import kotlinx.coroutines.launch

// Chọn "Class" khi tạo file này nhé Việt!
class LearningActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLearningBinding
    private var flashcards: List<Flashcard> = listOf()
    private var currentIndex = 0
    private var isFront = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Lấy ID của bộ thẻ từ màn hình chính gửi sang
        val deckId = intent.getLongExtra("DECK_ID", -1)

        // 2. Lấy danh sách thẻ từ Room Database
        val database = AppDatabase.getDatabase(this)
        lifecycleScope.launch {
            flashcards = database.flashcardDao().getFlashcardsForDeck(deckId)
            if (flashcards.isNotEmpty()) {
                showCard()
            }
        }

        // 3. Chạm vào thẻ để lật mặt
        binding.root.setOnClickListener {
            isFront = !isFront
            showCard()
        }

        // 4. Các nút chọn mức độ học (SM-2)
        binding.btnEasy.setOnClickListener { handleAnswer(5) }
        binding.btnHard.setOnClickListener { handleAnswer(1) }
    }

    private fun showCard() {
        if (flashcards.isEmpty()) return
        val currentCard = flashcards[currentIndex]
        // Hiển thị mặt trước hoặc mặt sau tùy trạng thái isFront
        binding.tvContent.text = if (isFront) currentCard.frontText else currentCard.backText
    }

    private fun handleAnswer(quality: Int) {
        val currentCard = flashcards[currentIndex]
        // Gọi thuật toán SM-2 ông đã có sẵn trong file SM2Logic
        val updatedCard = SM2Logic.calculate(currentCard, quality)

        lifecycleScope.launch {
            AppDatabase.getDatabase(this@LearningActivity).flashcardDao().updateFlashcard(updatedCard)

            if (currentIndex < flashcards.size - 1) {
                currentIndex++
                isFront = true
                showCard()
            } else {
                finish() // Hết thẻ thì biến về màn hình chính
            }
        }
    }
}