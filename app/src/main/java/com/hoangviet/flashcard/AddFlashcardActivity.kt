package com.hoangviet.flashcard

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddFlashcardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_flashcard)

        val etFront = findViewById<EditText>(R.id.etFront)
        val etBack = findViewById<EditText>(R.id.etBack)
        val btnSave = findViewById<Button>(R.id.btnSaveFlashcard)

        btnSave.setOnClickListener {
            val frontText = etFront.text.toString()
            val backText = etBack.text.toString()

            if (frontText.isNotEmpty() && backText.isNotEmpty()) {
                lifecycleScope.launch {
                    val newCard = Flashcard(deckId = 1, front = frontText, back = backText)
                    AppDatabase.getDatabase(this@AddFlashcardActivity).flashcardDao().insert(newCard)

                    runOnUiThread {
                        Toast.makeText(this@AddFlashcardActivity, "Đã lưu thẻ!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Nhập đủ thông tin đã ông giáo ơi!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}