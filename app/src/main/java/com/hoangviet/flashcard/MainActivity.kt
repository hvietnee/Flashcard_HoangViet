package com.hoangviet.flashcard

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.hoangviet.flashcard.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tts: TextToSpeech
    private val viewModel: FlashcardViewModel by viewModels {
        FlashcardViewModelFactory((application as FlashcardApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)

        val adapter = DeckAdapter(
            onClick = { card ->
                val intent = Intent(this, LearningActivity::class.java)
                intent.putExtra("START_CARD_ID", card.id)
                startActivity(intent)
            },
            onSpeakClick = { tts.speak(it, TextToSpeech.QUEUE_FLUSH, null, null) },
            onLongClick = { card, view -> showEditMenu(card, view) }
        )

        binding.rvDecks.adapter = adapter
        binding.rvDecks.layoutManager = LinearLayoutManager(this)
        viewModel.allFlashcards.observe(this) { adapter.submitList(it) }

        binding.fabSetReminder.setOnClickListener { showTimePicker() }
        binding.fabAddDeck.setOnClickListener { showAddDialog() }
    }

    private fun showAddDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Thêm thẻ mới")
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 20, 50, 20)
        }
        val inputEng = EditText(this).apply { hint = "Tiếng Anh" }
        val inputVie = EditText(this).apply { hint = "Tiếng Việt" }
        layout.addView(inputEng); layout.addView(inputVie)
        builder.setView(layout)

        builder.setPositiveButton("Thêm") { _, _ ->
            val eng = inputEng.text.toString()
            val vie = inputVie.text.toString()
            if (eng.isNotEmpty()) {
                lifecycleScope.launch {
                    (application as FlashcardApplication).database.flashcardDao().insert(Flashcard(front = eng, back = vie))
                }
            }
        }
        builder.setNegativeButton("Hủy", null)
        builder.show()
    }

    private fun showEditMenu(card: Flashcard, view: android.view.View) {
        val popup = PopupMenu(this, view)
        popup.menu.add("Sửa"); popup.menu.add("Xóa")
        popup.setOnMenuItemClickListener { item ->
            when (item.title) {
                "Xóa" -> lifecycleScope.launch { (application as FlashcardApplication).database.flashcardDao().delete(card) }
                "Sửa" -> showEditDialog(card)
            }
            true
        }
        popup.show()
    }

    private fun showEditDialog(card: Flashcard) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sửa từ")
        val input = EditText(this).apply { setText(card.front) }
        builder.setView(input)
        builder.setPositiveButton("Lưu") { _, _ ->
            card.front = input.text.toString()
            lifecycleScope.launch {
                (application as FlashcardApplication).database.flashcardDao().update(card)
            }
        }
        builder.setNegativeButton("Hủy", null)
        builder.show()
    }

    private fun showTimePicker() {
        val c = Calendar.getInstance()
        TimePickerDialog(this, { _, hour, min ->
            scheduleNotification(hour, min)
            Toast.makeText(this, "Hẹn nhắc lúc $hour:$min", Toast.LENGTH_SHORT).show()
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()
    }

    private fun scheduleNotification(hour: Int, min: Int) {
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, min)
            set(Calendar.SECOND, 0)
        }
        if (target.before(Calendar.getInstance())) target.add(Calendar.DATE, 1)

        val delay = target.timeInMillis - System.currentTimeMillis()
        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()
        WorkManager.getInstance(this).enqueueUniqueWork("HOC_TAP", ExistingWorkPolicy.REPLACE, workRequest)
    }

    override fun onInit(status: Int) { if (status == TextToSpeech.SUCCESS) tts.language = Locale.US }
}