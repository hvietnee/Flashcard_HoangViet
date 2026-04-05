package com.hoangviet.flashcard

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hoangviet.flashcard.databinding.ItemDeckBinding

class DeckAdapter : ListAdapter<Deck, DeckAdapter.DeckViewHolder>(DeckDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
        val binding = ItemDeckBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeckViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        val deck = getItem(position)
        holder.bind(deck)

        // KHI NHẤN VÀO THẺ: Dẫn thẳng tới màn hình học tập LearningActivity
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, LearningActivity::class.java)

            // Gửi ID của bộ thẻ đi theo để máy biết cần học bộ nào
            intent.putExtra("DECK_ID", deck.id)

            context.startActivity(intent)
        }
    }

    class DeckViewHolder(private val binding: ItemDeckBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(deck: Deck) {
            binding.tvDeckName.text = deck.name
            binding.tvDeckDescription.text = deck.description
        }
    }

    class DeckDiffCallback : DiffUtil.ItemCallback<Deck>() {
        override fun areItemsTheSame(oldItem: Deck, newItem: Deck) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Deck, newItem: Deck) = oldItem == newItem
    }
}