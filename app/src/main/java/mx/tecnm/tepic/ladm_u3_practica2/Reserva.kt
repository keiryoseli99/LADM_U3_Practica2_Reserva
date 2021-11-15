package mx.tecnm.tepic.ladm_u3_practica2

import android.content.ContentValues
import android.content.Context

class Reserva(p: Context) {
    var lugar = ""
    var hora = ""
    var fecha = ""
    var descripcion = ""
    var cliente = ""
    var pnt = p

    fun insertarReserva() : Boolean {
        val tabla = DataBase(pnt,"dbReserva",null,1).writableDatabase
        val datos = ContentValues()

        datos.put("lugar",lugar)
        datos.put("hora",hora)
        datos.put("fecha",fecha)
        datos.put("descripcion",descripcion)
        datos.put("cliente",cliente)

        val resultado = tabla.insert("RESERVA",null,datos)
        if (resultado == -1L){
            return false
        }
        return true
    }

    fun eliminarReserva(Eliminar:String) : Boolean {
        val tabla = DataBase(pnt,"dbReserva",null,1).writableDatabase
        val resultado = tabla.delete("RESERVA", "CLIENTE=?", arrayOf(Eliminar.toString()))
        if (resultado == 0) return false
        return true
    }

    fun actualizarReserva(Actualizar: String): Boolean {
        val tabla = DataBase(pnt,"dbReserva",null,1).writableDatabase
        val datos = ContentValues()

        datos.put("lugar",lugar)
        datos.put("hora",hora)
        datos.put("fecha",fecha)
        datos.put("descripcion",descripcion)
        datos.put("cliente",cliente)

        val resultado = tabla.update("RESERVA",datos,"CLIENTE=?", arrayOf(Actualizar))
        if (resultado==0) return false
        return true
    }
}