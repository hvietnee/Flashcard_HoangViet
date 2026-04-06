package com.hoangviet.flashcard

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

// QUAN TRỌNG: Ông phải dán đúng cái ngoặc này thì bên dưới mới hết đỏ
class DeckAdapter(
    private val onClick: (Flashcard) -> Unit,
    private val onSpeakClick: (String) -> Unit,
    private val onLongClick: (Flashcard, View) -> Unit
) : RecyclerView.Adapter<DeckAdapter.DeckViewHolder>() {

    private var list = listOf<Flashcard>()
    fun submitList(newList: List<Flashcard>) { list = newList; notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_deck, parent, false)
        return DeckViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        val item = list[position]
        holder.tvName.text = item.front

        // TÔ MÀU: Cam (Chưa thuộc), Xanh (Đã thuộc)
        when (item.status) {
            1 -> holder.cardRoot.setCardBackgroundColor(Color.parseColor("#FF9800"))
            2 -> holder.cardRoot.setCardBackgroundColor(Color.parseColor("#2196F3"))
            else -> holder.cardRoot.setCardBackgroundColor(Color.WHITE)
        }

        // HẾT LỖI ĐỎ: Vì đã khai báo ở trên
        holder.btnSpeak.setOnClickListener { onSpeakClick(item.front) }
        holder.itemView.setOnClickListener { onClick(item) }
        holder.itemView.setOnLongClickListener { onLongClick(item, it); true }
    }

    override fun getItemCount() = list.size

    class DeckViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvDeckName)
        val btnSpeak: ImageButton = view.findViewById(R.id.btnSpeak)
        val cardRoot: CardView = view.findViewById(R.id.cardItemRoot)
    }
}