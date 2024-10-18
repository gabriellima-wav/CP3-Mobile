package com.example.cp3_mobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WatchAdapter(
    private val watches: List<Watch>,
    private val onItemClick: (Watch) -> Unit
) : RecyclerView.Adapter<WatchAdapter.WatchViewHolder>() {

    inner class WatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewWatch: ImageView = itemView.findViewById(R.id.imageViewWatch)
        val textViewWatchName: TextView = itemView.findViewById(R.id.textViewWatchName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_watch, parent, false)
        return WatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: WatchViewHolder, position: Int) {
        val watch = watches[position]
        holder.imageViewWatch.setImageResource(watch.imageResId)
        holder.textViewWatchName.text = watch.name

        holder.itemView.setOnClickListener {
            onItemClick(watch)
        }
    }

    override fun getItemCount(): Int {
        return watches.size
    }
}
