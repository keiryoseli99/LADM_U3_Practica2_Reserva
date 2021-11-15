package mx.tecnm.tepic.ladm_u3_practica2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mx.tecnm.tepic.ladm_u3_practica2.databinding.ActivityMain2Binding
import java.lang.Exception


class MainActivity2 : AppCompatActivity() {
    private  lateinit var binding2: ActivityMain2Binding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2 = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding2.root)

        var btnEditar = binding2.editar;
        var btnBorrar = binding2.borrar

        btnEditar.visibility = View.GONE;
        btnBorrar.visibility = View.GONE;

        var test = "";

        binding2.buscar.setOnClickListener {
            var cliente = binding2.cliente2.text.toString()
            if(!cliente.isEmpty()){
                leerDatos(cliente);
            }else{
                Toast.makeText(this,"No se encontró el cliente",Toast.LENGTH_LONG).show()
            }
        }
    }


    public fun leerDatos(cliente: String) {
        database = FirebaseDatabase.getInstance().getReference("eventos")
        database.child(cliente).get().addOnSuccessListener {

            if(it.exists() ){
                Toast.makeText(this,"Cliente encontrado",Toast.LENGTH_SHORT).show()

                val cliente = it.child("cliente")?.getValue().toString()
                val descripcion = it.child("descripcion")?.getValue().toString()
                val fecha = it.child("fecha")?.getValue().toString()
                val hora = it.child("hora")?.getValue().toString()
                val lugar = it.child("lugar")?.getValue().toString()

                binding2.descripcion.setText(descripcion)
                binding2.fecha.setText(fecha)
                binding2.hora.setText(hora)
                binding2.lugar.setText(lugar)

                var btnEditar = binding2.editar;
                var btnBorrar = binding2.borrar
                btnEditar.visibility = View.VISIBLE;
                btnBorrar.visibility = View.VISIBLE;

                binding2.editar.setOnClickListener {
                    actualizarDatos(cliente)
                }
                binding2.borrar.setOnClickListener {
                    borrarDatos(cliente)
                }

            }else{
                Toast.makeText(this,"Error al cargar los datos",Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener{
            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
        }
    }

    private fun borrarDatos(cliente: String) {
        //Firestore
        database = FirebaseDatabase.getInstance().getReference("eventos")
        database.child(cliente).removeValue().addOnSuccessListener {

            Toast.makeText(this,"Exito al borrar",Toast.LENGTH_SHORT).show()

            var activity1 = Intent(this, MainActivity::class.java)
            startActivity(activity1)

        }.addOnFailureListener{
            Toast.makeText(this,"Error al borrar",Toast.LENGTH_SHORT).show()
        }

        //SQLite
        try {
            Reserva(this).eliminarReserva(cliente)
            binding2.lugar.text.clear()
            binding2.hora.text.clear()
            binding2.fecha.text.clear()
            binding2.descripcion.text.clear()
            Toast.makeText(this,"Exito al borrar SQLite",Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Toast.makeText(this,"Error al borrar SQLite",Toast.LENGTH_SHORT).show()
        }//Fin SQLite
    }

    private fun actualizarDatos(cliente: String) {
        //Firestore
        var descripcion =binding2.descripcion.text.toString()
        var fecha = binding2.fecha.text.toString()
        var hora = binding2.hora.text.toString()
        var lugar =binding2.lugar.text.toString()

        database = FirebaseDatabase.getInstance().getReference("eventos")

        val evento = mapOf<String,String>(
            "descripcion" to descripcion,
            "fecha" to fecha,
            "hora" to hora,
            "lugar" to lugar
        )
        println(evento)

        database.child(cliente).updateChildren(evento).addOnSuccessListener {
            binding2.lugar.text.clear()
            binding2.hora.text.clear()
            binding2.fecha.text.clear()
            binding2.descripcion.text.clear()
            binding2.cliente2.text.clear()

            Toast.makeText(this,"Exito al actualizar los datos",Toast.LENGTH_SHORT).show()

            var btnEditar = binding2.editar;
            var btnBorrar = binding2.borrar
            btnEditar.visibility = View.GONE;
            btnBorrar.visibility = View.GONE;

        }.addOnFailureListener {
            Toast.makeText(this,"Error al actualizar los datos",Toast.LENGTH_SHORT).show()
        }

        //SQLite
        val resultado = Reserva(this).actualizarReserva(cliente)
        if (resultado) {
            Toast.makeText(this, "EXITO SE ACTUALIZO SQLite", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "ERROR! NO SE LOGRO ACTUALIZAR SQLite", Toast.LENGTH_LONG).show()
        }//Fin SQLite
    }
}