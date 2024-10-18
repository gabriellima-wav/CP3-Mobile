package com.example.cp3_mobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cp3_mobile.database.DatabaseHelper


class CatalogActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerViewWatches: RecyclerView
    private lateinit var watchAdapter: WatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        databaseHelper = DatabaseHelper(this)
        recyclerViewWatches = findViewById(R.id.recyclerViewWatches)
        recyclerViewWatches.layoutManager = GridLayoutManager(this, 2)

        val watches = databaseHelper.getAllWatches()

        watchAdapter = WatchAdapter(watches) { watch ->
            openWatchDetails(watch.id)
        }

        recyclerViewWatches.adapter = watchAdapter
    }

    private fun openWatchDetails(watchId: Long) {
        val intent = Intent(this, WatchDetailsActivity::class.java)
        intent.putExtra("WATCH_ID", watchId)
        startActivity(intent)
    }
}
