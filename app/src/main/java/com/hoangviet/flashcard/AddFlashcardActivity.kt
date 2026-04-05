package com.hoangviet.flashcard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hoangviet.flashcard.databinding.ActivityAddFlashcardBinding
import kotlinx.coroutines.launch

class AddFlashcardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFlashcardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFlashcardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lấy ID của bộ thẻ từ màn hình chính gửi sang
        val deckId = intent.getLongExtra("DECK_ID", -1)

        binding.btnSave.setOnClickListener {
            val frontText = binding.edtFront.text.toString()
            val backText = binding.edtBack.text.toString()

            if (frontText.isNotEmpty() && backText.isNotEmpty()) {
                val newCard = Flashcard(deckId = deckId, front = frontText, back = backText)

                lifecycleScope.launch {
                    val database = AppDatabase.getDatabase(this@AddFlashcardActivity)
                    database.flashcardDao().insert(newCard)
                    finish() // Lưu xong đóng màn hình thêm thẻ
                }
            }
        }
    }
}