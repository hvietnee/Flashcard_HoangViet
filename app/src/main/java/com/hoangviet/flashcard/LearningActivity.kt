package com.hoangviet.flashcard

import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.hoangviet.flashcard.databinding.ActivityLearningBinding
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.concurrent.TimeUnit

class LearningActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityLearningBinding
    private lateinit var tts: TextToSpeech
    private var flashcardList: List<Flashcard> = listOf()
    private var currentIndex = 0
    private var isShowingFront = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tts = TextToSpeech(this, this)
        val startId = intent.getIntExtra("START_CARD_ID", -1)

        lifecycleScope.launch {
            flashcardList = AppDatabase.getDatabase(this@LearningActivity).flashcardDao().getAllFlashcardsOnce()
            currentIndex = flashcardList.indexOfFirst { it.id == startId }.takeIf { it != -1 } ?: 0
            showCard()
        }

        binding.cardView.setOnClickListener {
            if (isShowingFront) {
                binding.tvFront.text = flashcardList[currentIndex].back
                isShowingFront = false
            } else {
                binding.tvFront.text = flashcardList[currentIndex].front
                isShowingFront = true
                tts.speak(flashcardList[currentIndex].front, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }

        binding.btnUnknown.setOnClickListener { updateAndNext(1) }
        binding.btnKnown.setOnClickListener { updateAndNext(2) }
    }

    private fun showCard() {
        if (currentIndex < flashcardList.size) {
            val card = flashcardList[currentIndex]
            binding.tvFront.text = card.front
            isShowingFront = true
            val color = when(card.status) {
                1 -> "#FF9800" // Cam
                2 -> "#2196F3" // Xanh
                else -> "#FFFFFF"
            }
            binding.cardView.setCardBackgroundColor(Color.parseColor(color))
        } else {
            Toast.makeText(this, "Học xong rồi Việt ơi!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun updateAndNext(status: Int) {
        val card = flashcardList[currentIndex]

        val updatedCard = SM2Logic.updateCardForDemo(card, status)

        lifecycleScope.launch {

            AppDatabase.getDatabase(this@LearningActivity).flashcardDao().update(updatedCard)

            scheduleReminder(updatedCard)

            currentIndex++
            showCard()
        }
    }

    private fun scheduleReminder(card: Flashcard) {
        val delay = card.nextReview - System.currentTimeMillis()
        if (delay > 0) {
            val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(this).enqueueUniqueWork(
                "REMIND_${card.id}",
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
        }
    }

    override fun onInit(status: Int) { if (status == TextToSpeech.SUCCESS) tts.language = Locale.US }
    override fun onDestroy() { tts.shutdown(); super.onDestroy() }
}