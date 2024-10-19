package com.example.cp3_mobile


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3_mobile.database.DatabaseHelper

class WatchFormActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var inputName: EditText
    private lateinit var inputDescription: EditText
    private lateinit var inputBrand: EditText
    private lateinit var inputPrice: EditText
    private lateinit var inputReleaseDate: EditText
    private lateinit var imageViewWatch: ImageView
    private lateinit var btnSelectImage: Button
    private lateinit var saveButton: Button

    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null // Armazena o URI da imagem selecionada

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
        imageViewWatch = findViewById(R.id.imageViewWatch)
        btnSelectImage = findViewById(R.id.btnSelectImage)
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
                selectedImageUri = Uri.parse(it.imageUri) // Recupera o URI como Uri
                imageViewWatch.setImageURI(selectedImageUri)
            }
        }

        btnSelectImage.setOnClickListener {
            openGallery() // Abre a galeria para selecionar a imagem
        }

        saveButton.setOnClickListener {
            saveWatch()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun saveWatch() {
        // Validação dos campos obrigatórios
        if (inputName.text.isNullOrEmpty() || inputDescription.text.isNullOrEmpty() || inputBrand.text.isNullOrEmpty() ||
            inputPrice.text.isNullOrEmpty() || inputReleaseDate.text.isNullOrEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        // Converte o preço para Double e trata erros
        val price: Double = try {
            inputPrice.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Preço inválido.", Toast.LENGTH_SHORT).show()
            return
        }

        // Pega o URI da imagem selecionada ou define um padrão se não houver
        val imageUriString = selectedImageUri?.toString() ?: ""

        // Cria ou atualiza o relógio
        val watch = Watch(
            id = watchId ?: 0L,
            name = inputName.text.toString(),
            description = inputDescription.text.toString(),
            imageUri = imageUriString, // Armazena o URI como String
            brand = inputBrand.text.toString(),
            price = price,
            releaseDate = inputReleaseDate.text.toString()
        )

        if (watchId != -1L) {
            databaseHelper.updateWatch(watch)
            Toast.makeText(this, "Relógio atualizado com sucesso!", Toast.LENGTH_SHORT).show()
        } else {
            databaseHelper.insertWatch(watch)
            Toast.makeText(this, "Relógio criado com sucesso!", Toast.LENGTH_SHORT).show()
        }

        finish() // Volta para a tela anterior
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data // Obtém o URI da imagem selecionada
            imageViewWatch.setImageURI(selectedImageUri)
        }
    }
}
