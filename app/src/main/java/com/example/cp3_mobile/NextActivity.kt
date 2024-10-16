package com.example.cp3mobile  // Certifique-se de que o pacote esteja correto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3_mobile.R

class NextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)  // Verifique se o layout existe
    }
}
