package com.hoangviet.flashcard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val viewModel: FlashcardViewModel by viewModels {
        FlashcardViewModelFactory(AppDatabase.getDatabase(this).flashcardDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val mainView = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rvDecks)
        val adapter = DeckAdapter()
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        viewModel.allFlashcards.observe(this) { listFlashcards ->
            adapter.submitList(listFlashcards.map {
                Deck(name = it.front, description = it.back)
            })
        }

        findViewById<View>(R.id.fabAddDeck).setOnClickListener {
            startActivity(Intent(this, AddFlashcardActivity::class.java))
        }
    }
}