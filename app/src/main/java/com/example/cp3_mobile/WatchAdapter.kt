package com.example.cp3_mobile

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class WatchAdapter(
    private var watches: List<Watch>,
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

        // Carregar a imagem usando o Glide
        if (!watch.imageUri.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(Uri.parse(watch.imageUri))
                .into(holder.imageViewWatch)
        } else {
            // Opcional: definir uma imagem placeholder caso imageUri seja nulo ou vazio
            holder.imageViewWatch.setImageResource(R.drawable.relogio_default_image)
        }

        // Exibir o nome do relógio
        holder.textViewWatchName.text = watch.name

        // Configurar o clique do item
        holder.itemView.setOnClickListener {
            onItemClick(watch)
        }
    }

    override fun getItemCount(): Int {
        return watches.size
    }

    // Método para atualizar a lista de relógios
    fun updateWatches(newWatches: List<Watch>) {
        this.watches = newWatches
        notifyDataSetChanged()
    }
}
