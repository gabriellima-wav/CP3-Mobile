package com.example.cp3mobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cp3_mobile.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Encontre o botão no layout
        val nextButton: Button = findViewById(R.id.next_button)

        // Configure um ouvinte de clique para o botão
        nextButton.setOnClickListener {
            // Inicie a NextActivity
            val intent = Intent(this, NextActivity::class.java)
            startActivity(intent)
        }
    }
}
