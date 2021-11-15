package mx.tecnm.tepic.ladm_u3_practica2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context,name,factory,version) {

    override fun onCreate(p: SQLiteDatabase) {
        p.execSQL("CREATE TABLE RESERVA( IDRESERVA INTEGER PRIMARY KEY AUTOINCREMENT, LUGAR VARCHAR(200), HORA VARCHAR(50), FECHA VARCHAR(50), DESCRIPCION VARCHAR(500), CLIENTE VARCHAR(200))")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}