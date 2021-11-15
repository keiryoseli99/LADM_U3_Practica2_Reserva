package mx.tecnm.tepic.ladm_u3_practica2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mx.tecnm.tepic.ladm_u3_practica2.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reservar.setOnClickListener {

            //Firestore
            val lugar = binding.lugar.text.toString()
            val hora = binding.hora.text.toString()
            val fecha = binding.fecha.text.toString()
            val descripcion = binding.descripcion.text.toString()
            val cliente = binding.cliente.text.toString()

            database = FirebaseDatabase.getInstance().getReference("eventos")

            val Evento = Evento(lugar,hora,fecha,descripcion,cliente)

            database.child(cliente).setValue(Evento).addOnSuccessListener {
                binding.lugar.text.clear()
                binding.hora.text.clear()
                binding.fecha.text.clear()
                binding.descripcion.text.clear()
                binding.cliente.text.clear()
                Toast.makeText(this,"Evento guardado",Toast.LENGTH_LONG).show()

            }.addOnFailureListener{
                Toast.makeText(this,"Algo falló ",Toast.LENGTH_LONG).show()
            }

            //SQLite
            val reserva = Reserva(this)
            reserva.lugar = lugar
            reserva.hora = hora
            reserva.fecha = fecha
            reserva.descripcion = descripcion
            reserva.cliente =cliente
            val resultado = reserva.insertarReserva()
            if (resultado) {
                Toast.makeText(this, "SE AGREGO CON EXITO SQLite!!!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "ERROR NO SE AGREGO SQLite :(", Toast.LENGTH_LONG).show()
            }//Fin SQLite
        }

        binding.buscar.setOnClickListener {
            var activity2 = Intent(this, MainActivity2::class.java)
            startActivity(activity2)
        }
    }
}