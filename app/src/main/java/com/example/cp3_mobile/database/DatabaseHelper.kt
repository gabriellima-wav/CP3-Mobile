package com.example.cp3_mobile.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cp3_mobile.R
import com.example.cp3_mobile.Watch

class DatabaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "watches.db"
        private const val DATABASE_VERSION = 3 // Incrementado para forçar a atualização
        private const val TABLE_WATCHES = "watches"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_IMAGE_URI = "image_uri" // Armazena URI como String
        private const val COLUMN_BRAND = "brand"
        private const val COLUMN_PRICE = "price"
        private const val COLUMN_RELEASE_DATE = "release_date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_WATCHES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_DESCRIPTION TEXT, " +
                "$COLUMN_IMAGE_URI TEXT, " + // Armazena URI da imagem como String
                "$COLUMN_BRAND TEXT, " +
                "$COLUMN_PRICE REAL, " +
                "$COLUMN_RELEASE_DATE TEXT)")
        db?.execSQL(createTable)
        insertInitialData(db)
    }

    private fun insertInitialData(db: SQLiteDatabase?) {
        val watches = listOf(
            Watch(0, "Relógio Dourado", "Um lindo relógio dourado.", getResourceUriString(R.drawable.relogio1), "Rolex", 5000.0, "2022-01-01"),
            Watch(0, "Relógio Prata", "Elegância e simplicidade.", getResourceUriString(R.drawable.relogio2), "Omega", 3500.0, "2021-11-10"),
            Watch(0, "Relógio Azul", "Relógio esportivo azul.", getResourceUriString(R.drawable.relogio3), "Casio", 1200.0, "2020-05-05"),
            Watch(0, "Relógio Preto", "O clássico que nunca sai de moda.", getResourceUriString(R.drawable.relogio4), "Seiko", 2000.0, "2019-10-15"),
            Watch(0, "Relógio Prata e Verde", "Design moderno com toque clássico.", getResourceUriString(R.drawable.relogio5), "Tag Heuer", 6000.0, "2021-03-21"),
            Watch(0, "SmartWatch Preto", "Tecnologia e estilo.", getResourceUriString(R.drawable.relogio6), "Apple", 4000.0, "2023-08-10")
        )

        for (watch in watches) {
            val values = ContentValues().apply {
                put(COLUMN_NAME, watch.name)
                put(COLUMN_DESCRIPTION, watch.description)
                put(COLUMN_IMAGE_URI, watch.imageUri)
                put(COLUMN_BRAND, watch.brand)
                put(COLUMN_PRICE, watch.price)
                put(COLUMN_RELEASE_DATE, watch.releaseDate)
            }
            db?.insert(TABLE_WATCHES, null, values)
        }
    }

    private fun getResourceUriString(resId: Int): String {
        return "android.resource://${context.packageName}/$resId"
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Descarta a tabela antiga e cria uma nova
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_WATCHES")
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getAllWatches(): List<Watch> {
        val watches = mutableListOf<Watch>()
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_WATCHES", null)
            if (cursor.moveToFirst()) {
                do {
                    val watch = Watch(
                        id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                        imageUri = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URI)),
                        brand = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BRAND)),
                        price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)),
                        releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RELEASE_DATE))
                    )
                    watches.add(watch)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            db.close()
        }
        return watches
    }

    @SuppressLint("Range")
    fun getWatchById(id: Long): Watch? {
        val db = this.readableDatabase
        var cursor: Cursor? = null
        var watch: Watch? = null
        try {
            cursor = db.query(
                TABLE_WATCHES,
                null,
                "$COLUMN_ID = ?",
                arrayOf(id.toString()),
                null,
                null,
                null
            )
            if (cursor.moveToFirst()) {
                watch = Watch(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    imageUri = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URI)),
                    brand = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BRAND)),
                    price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)),
                    releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RELEASE_DATE))
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            db.close()
        }
        return watch
    }

    fun insertWatch(watch: Watch): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, watch.name)
            put(COLUMN_DESCRIPTION, watch.description)
            put(COLUMN_IMAGE_URI, watch.imageUri)
            put(COLUMN_BRAND, watch.brand)
            put(COLUMN_PRICE, watch.price)
            put(COLUMN_RELEASE_DATE, watch.releaseDate)
        }
        val id = db.insert(TABLE_WATCHES, null, values)
        db.close()
        return id
    }

    fun updateWatch(watch: Watch): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, watch.name)
            put(COLUMN_DESCRIPTION, watch.description)
            put(COLUMN_IMAGE_URI, watch.imageUri)
            put(COLUMN_BRAND, watch.brand)
            put(COLUMN_PRICE, watch.price)
            put(COLUMN_RELEASE_DATE, watch.releaseDate)
        }
        val rowsAffected = db.update(TABLE_WATCHES, values, "$COLUMN_ID = ?", arrayOf(watch.id.toString()))
        db.close()
        return rowsAffected
    }

    fun deleteWatch(id: Long): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete(TABLE_WATCHES, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }
}
