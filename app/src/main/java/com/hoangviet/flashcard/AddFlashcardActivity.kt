package com.hoangviet.flashcard

import android.os.Bundle
import android.widget.Toast
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

        binding.btnSave.setOnClickListener {
            val front = binding.edtFront.text.toString()
            val back = binding.edtBack.text.toString()

            if (front.isNotEmpty() && back.isNotEmpty()) {
                val newCard = Flashcard(front = front, back = back)
                lifecycleScope.launch {

                    (application as FlashcardApplication).repository.insert(newCard)
                    Toast.makeText(this@AddFlashcardActivity, "Đã lưu thẻ!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Việt ơi nhập đủ 2 mặt đi!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}