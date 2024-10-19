package com.example.cp3_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cp3_mobile.database.DatabaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CatalogActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerViewWatches: RecyclerView
    private lateinit var watchAdapter: WatchAdapter
    private lateinit var addButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        databaseHelper = DatabaseHelper(this)
        recyclerViewWatches = findViewById(R.id.recyclerViewWatches)
        recyclerViewWatches.layoutManager = GridLayoutManager(this, 2)

        val watches = databaseHelper.getAllWatches()
        Log.d("CatalogActivity", "Número de relógios recuperados: ${watches.size}")

        watchAdapter = WatchAdapter(watches) { watch ->
            openWatchDetails(watch.id)
        }

        recyclerViewWatches.adapter = watchAdapter

        addButton = findViewById(R.id.add_button)
        addButton.setOnClickListener {
            val intent = Intent(this, WatchFormActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Atualiza a lista ao retornar para a tela de catálogo
        val updatedWatches = databaseHelper.getAllWatches()
        watchAdapter.updateWatches(updatedWatches)
    }

    private fun openWatchDetails(watchId: Long) {
        val intent = Intent(this, WatchDetailsActivity::class.java)
        intent.putExtra("WATCH_ID", watchId)
        startActivity(intent)
    }
}
