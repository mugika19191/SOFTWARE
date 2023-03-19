package com.example.final_proyect

import android.text.Layout
import android.view. LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class CustomAdapter: RecyclerView.Adapter<CustomAdapter. ViewHolder>() {
    val titles = arrayOf("One Piece","Vinland Saga","Sonny Boy")
    val images = arrayOf(R.drawable.book_icon,R.drawable.book_icon,R.drawable.book_icon)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = android.view.LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder (viewHolder: ViewHolder, i: Int) {
        viewHolder.content_title.text = titles [i]
        viewHolder.content_image.setImageResource(images[i])
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var content_image: ImageView
        var content_title: TextView

        init {
            content_image = itemView.findViewById(R.id.content_image)
            content_title = itemView.findViewById(R.id.content_title)
        }
    }
}