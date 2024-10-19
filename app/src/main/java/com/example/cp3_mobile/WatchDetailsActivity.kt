package com.example.cp3_mobile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cp3_mobile.database.DatabaseHelper

class WatchDetailsActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var textViewWatchName: TextView
    private lateinit var textViewWatchDescription: TextView
    private lateinit var imageViewWatch: ImageView
    private lateinit var textViewWatchPrice: TextView
    private lateinit var btnEditWatch: Button
    private lateinit var btnDeleteWatch: Button

    private var watchId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_details)

        // Inicializa os componentes de UI
        databaseHelper = DatabaseHelper(this)
        textViewWatchName = findViewById(R.id.tvWatchName)
        textViewWatchDescription = findViewById(R.id.tvWatchDescription)
        imageViewWatch = findViewById(R.id.ivWatchImage)
        textViewWatchPrice = findViewById(R.id.tvWatchPrice)
        btnEditWatch = findViewById(R.id.btnEditWatch)
        btnDeleteWatch = findViewById(R.id.btnDeleteWatch)

        // Obtém o ID do relógio passado pela Intent
        watchId = intent.getLongExtra("WATCH_ID", -1)

        if (watchId != -1L) {
            // Busca o relógio no banco de dados
            val watch = databaseHelper.getWatchById(watchId)
            if (watch != null) {
                // Exibe as informações do relógio na UI
                textViewWatchName.text = watch.name
                textViewWatchDescription.text = watch.description

                // Carrega a imagem usando o Glide
                if (!watch.imageUri.isNullOrEmpty()) {
                    Glide.with(this)
                        .load(Uri.parse(watch.imageUri))
                        .into(imageViewWatch)
                } else {
                    imageViewWatch.setImageResource(R.drawable.relogio_default_image)
                }

                textViewWatchPrice.text = "R$ ${watch.price}"

                // Configura os botões de Editar e Apagar
                btnEditWatch.setOnClickListener {
                    val intent = Intent(this, WatchFormActivity::class.java)
                    intent.putExtra("WATCH_ID", watchId)
                    startActivity(intent)
                }

                btnDeleteWatch.setOnClickListener {
                    databaseHelper.deleteWatch(watchId)
                    Toast.makeText(this, "Relógio apagado com sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Relógio não encontrado", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
