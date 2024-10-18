package com.example.cp3_mobile.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import com.example.cp3_mobile.R
import com.example.cp3_mobile.Watch

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "watches.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_WATCHES = "watches"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_IMAGE_RES_ID = "image_res_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_WATCHES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_DESCRIPTION TEXT, " +
                "$COLUMN_IMAGE_RES_ID INTEGER)")
        db?.execSQL(createTable)
        insertInitialData(db)
    }

    private fun insertInitialData(db: SQLiteDatabase?) {
        val watches = listOf(
            Watch(0, "Relógio Dourado", "Um lindo relógio dourado.", R.drawable.relogio1),
            Watch(0, "Relógio Prata", "Elegância e simplicidade.", R.drawable.relogio2),
            Watch(0, "Relógio Azul", "Relógio esportivo azul.", R.drawable.relogio3),
            Watch(0, "Relógio Preto", "O clássico que nunca sai de moda.", R.drawable.relogio4),
            Watch(0, "Relógio Prata e Verde", "Design moderno com toque clássico.", R.drawable.relogio5),
            Watch(0, "SmartWatch Preto", "Tecnologia e estilo.", R.drawable.relogio6)
        )

        for (watch in watches) {
            val values = ContentValues().apply {
                put(COLUMN_NAME, watch.name)
                put(COLUMN_DESCRIPTION, watch.description)
                put(COLUMN_IMAGE_RES_ID, watch.imageResId)
            }
            db?.insert(TABLE_WATCHES, null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_WATCHES")
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getAllWatches(): List<Watch> {
        val watches = mutableListOf<Watch>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_WATCHES", null)

        if (cursor.moveToFirst()) {
            do {
                val watch = Watch(
                    id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                    imageResId = cursor.getInt(cursor.getColumnIndex(COLUMN_IMAGE_RES_ID))
                )
                watches.add(watch)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return watches
    }

    @SuppressLint("Range")
    fun getWatchById(id: Long): Watch? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_WATCHES,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var watch: Watch? = null
        if (cursor.moveToFirst()) {
            watch = Watch(
                id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                imageResId = cursor.getInt(cursor.getColumnIndex(COLUMN_IMAGE_RES_ID))
            )
        }
        cursor.close()
        db.close()
        return watch
    }
}
