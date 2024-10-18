package com.example.cp3_mobile

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3_mobile.database.DatabaseHelper

class WatchDetailsActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var textViewWatchName: TextView
    private lateinit var textViewWatchDescription: TextView
    private lateinit var imageViewWatch: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_details)

        databaseHelper = DatabaseHelper(this)
        textViewWatchName = findViewById(R.id.tvWatchName)
        textViewWatchDescription = findViewById(R.id.tvWatchDescription)
        imageViewWatch = findViewById(R.id.ivWatchImage)

        val watchId = intent.getLongExtra("WATCH_ID", -1)

        if (watchId != -1L) {
            val watch = databaseHelper.getWatchById(watchId)
            if (watch != null) {
                textViewWatchName.text = watch.name
                textViewWatchDescription.text = watch.description
                imageViewWatch.setImageResource(watch.imageResId)
            } else {
                // Trate o caso de relógio não encontrado
            }
        } else {
            // Trate o caso de ID inválido
        }
    }
}
