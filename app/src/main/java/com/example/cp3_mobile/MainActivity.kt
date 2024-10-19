package com.example.cp3_mobile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var catalogButton: MaterialButton
    private lateinit var developersButton: MaterialButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        catalogButton = findViewById(R.id.catalog_button)
        developersButton = findViewById(R.id.developers_button)

        catalogButton.setOnClickListener {
            val intent = Intent(this, CatalogActivity::class.java)
            startActivity(intent)
        }

        developersButton.setOnClickListener {
            val intent = Intent(this, DevelopersActivity::class.java)
            startActivity(intent)
        }
    }
}
