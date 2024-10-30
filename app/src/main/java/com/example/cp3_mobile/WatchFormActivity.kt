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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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
    private var selectedImageUri: Uri? = null

    private var watchId: Long? = null
    private lateinit var firebaseDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_form)

        databaseHelper = DatabaseHelper(this)

        // Inicialize o Firebase Database
        firebaseDatabase = FirebaseDatabase.getInstance().reference.child("watches")

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
                selectedImageUri = Uri.parse(it.imageUri)
                imageViewWatch.setImageURI(selectedImageUri)
            }
        }

        btnSelectImage.setOnClickListener {
            openGallery()
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
        if (inputName.text.isNullOrEmpty() || inputDescription.text.isNullOrEmpty() ||
            inputBrand.text.isNullOrEmpty() || inputPrice.text.isNullOrEmpty() ||
            inputReleaseDate.text.isNullOrEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val price: Double = try {
            inputPrice.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Preço inválido.", Toast.LENGTH_SHORT).show()
            return
        }

        val imageUriString = selectedImageUri?.toString() ?: ""

        val watch = Watch(
            id = watchId ?: 0L,
            name = inputName.text.toString(),
            description = inputDescription.text.toString(),
            imageUri = imageUriString,
            brand = inputBrand.text.toString(),
            price = price,
            releaseDate = inputReleaseDate.text.toString()
        )

        // Gera um novo ID para o relógio no Firebase
        val newWatchId = firebaseDatabase.push().key
        newWatchId?.let {
            firebaseDatabase.child(it).setValue(watch)
                .addOnSuccessListener {
                    // Agora insira o relógio no banco de dados local
                    if (watchId == null) {
                        databaseHelper.insertWatch(watch) // Insere apenas se for um novo relógio
                    } else {
                        databaseHelper.updateWatch(watch) // Atualiza se já existir
                    }
                    Toast.makeText(this, "Relógio salvo no Firebase e no banco de dados local com sucesso!", Toast.LENGTH_SHORT).show()
                    finish() // Volta para a tela anterior
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Erro ao salvar no Firebase: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            imageViewWatch.setImageURI(selectedImageUri)
        }
    }
}
