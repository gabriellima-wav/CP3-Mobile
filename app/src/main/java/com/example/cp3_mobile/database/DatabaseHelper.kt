import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "watches.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_WATCHES = "watches"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_WATCHES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_DESCRIPTION TEXT)")
        db?.execSQL(createTable)

        // Inserir dados iniciais
        insertInitialData(db)
    }

    private fun insertInitialData(db: SQLiteDatabase?) {
        val watches = listOf(
            Watch(0, "Relógio Dourado", "Um lindo relógio dourado."),
            Watch(0, "Relógio Prata", "Elegância e simplicidade."),
            Watch(0, "Relógio Azul", "Relógio esportivo azul."),
            Watch(0, "Relógio Preto", "O clássico que nunca sai de moda.")
        )

        for (watch in watches) {
            val values = ContentValues().apply {
                put(COLUMN_NAME, watch.name)
                put(COLUMN_DESCRIPTION, watch.description)
            }
            db?.insert(TABLE_WATCHES, null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_WATCHES")
        onCreate(db)
    }

    // Métodos CRUD
    fun addWatch(name: String, description: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_DESCRIPTION, description)
        }
        val id = db.insert(TABLE_WATCHES, null, values)
        db.close()
        return id
    }

    fun updateWatch(id: Int, name: String, description: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_DESCRIPTION, description)
        }
        return db.update(TABLE_WATCHES, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun deleteWatch(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_WATCHES, "$COLUMN_ID = ?", arrayOf(id.toString()))
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
                    description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                )
                watches.add(watch)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return watches
    }
}
