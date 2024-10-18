package com.example.cp3_mobile

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3_mobile.database.DatabaseHelper

class WatchFormActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var inputName: EditText
    private lateinit var inputDescription: EditText
    private lateinit var inputBrand: EditText
    private lateinit var inputPrice: EditText
    private lateinit var inputReleaseDate: EditText
    private lateinit var saveButton: Button
    private var watchId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_form)

        databaseHelper = DatabaseHelper(this)

        inputName = findViewById(R.id.input_name)
        inputDescription = findViewById(R.id.input_description)
        inputBrand = findViewById(R.id.input_brand)
        inputPrice = findViewById(R.id.input_price)
        inputReleaseDate = findViewById(R.id.input_release_date)
        saveButton = findViewById(R.id.save_button)

        watchId = intent.getLongExtra("WATCH_ID", -1L)
        if (watchId != -1L) {
            val watch = databaseHelper.getWatchById(watchId!!)
            watch?.let {
                inputName.setText(it.name)
                inputDescription.setText(it.description)
                inputBrand.setText(it.brand)
                inputPrice.setText(it.price.toString())
                inputReleaseDate.setText(it.releaseDate)
            }
        }

        saveButton.setOnClickListener {
            val watch = Watch(
                id = watchId ?: 0L,
                name = inputName.text.toString(),
                description = inputDescription.text.toString(),
                imageResId = R.drawable.relogio1, // Pode ajustar para aceitar seleção de imagem
                brand = inputBrand.text.toString(),
                price = inputPrice.text.toString().toDouble(),
                releaseDate = inputReleaseDate.text.toString()
            )
            if (watchId != -1L) {
                databaseHelper.updateWatch(watch)
            } else {
                databaseHelper.insertWatch(watch)
            }
            finish() // Volta para a tela anterior
        }
    }
}
