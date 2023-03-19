package com.example.final_proyect

import android.annotation.SuppressLint
import android.app.DownloadManager.Request
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RestrictTo.Scope
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.rotationMatrix
import androidx.room.Room
import kotlinx.coroutines.launch
import androidx.lifecycle.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101
    var notiText = String()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadAllContent()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel: NotificationChannel = NotificationChannel (CHANNEL_ID, name, importance).apply{
            description= descriptionText
            }
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel (channel)
        }
    }
    @SuppressLint("MissingPermission")
    private fun sendNotification(){
        val builder = NotificationCompat. Builder( this, CHANNEL_ID)
            .setSmallIcon (R.drawable.book_icon)
            .setContentTitle("Contenido que se ha añadido")
            .setContentText (notiText)
            .setPriority (NotificationCompat.PRIORITY_DEFAULT)

        with (NotificationManagerCompat.from( this)){
            notify(notificationId, builder.build())
        }
    }

    fun changeInicio(view: View){
        setContentView(R.layout.inicio_sesion)
    }
    fun changeRegistro(view: View){
        setContentView(R.layout.registro)
    }
    fun Registrar(view: View){
        val room = Room.databaseBuilder(this, DBPruebas::class.java, "Usuario").build()
        lifecycleScope.launch {
            var name = findViewById<TextView>(R.id.name)
            var pass = findViewById<TextView>(R.id.pass)

            var user=room.daoUsuario().getByName(name.text.toString())
            if (name.text.toString()!="" && pass.text.toString() !=""){
                if (user==null){
                    room.daoUsuario().insert(Usuario( name.text.toString(), pass.text.toString(),""))
                    toast_manager("Registro Completado")
                }
                else{
                    toast_manager("Ya existe un usuario con ese nombre")
                }
            }
            else{
                toast_manager("Contraseña o nombre de usuario esta vacio.")
            }
        }

    }
    fun IniciarSesion(view: View){
        val room = Room.databaseBuilder(this, DBPruebas::class.java, "Usuario").build()
        lifecycleScope.launch {
            var name = findViewById<TextView>(R.id.name)
            var pass = findViewById<TextView>(R.id.pass)

            var user=room.daoUsuario().getByName(name.text.toString())
            if (user==null){
                toast_manager("El usuario no existe")
            }
            else
            {
                if (user.pass==pass.text.toString()){
                    toast_manager("Se ha iniciado sesion")
                    val intent = Intent(this@MainActivity, Perfil_Activity::class.java).also {
                        it.putExtra("NAME", user.name)
                        startActivity(it)
                    }
                }
                else{
                    toast_manager("La contraseña no es correcta")
                }
            }
        }

    }
    fun toast_manager(mensaje:String){
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(applicationContext, mensaje, duration)
        toast.show()
    }
    fun loadAllContent(){
        val room = Room.databaseBuilder(this, DBContent::class.java, "Content").build()

        lifecycleScope.launch {
            val allcontent = room.daoContent().getContents().size
            if (allcontent==0){
                showLoadDialog()
            }
        }
    }
    fun showLoadDialog(){
            MaterialAlertDialogBuilder(this)
                .setTitle("Alerta")
                .setMessage("No hay informacion sobre los conteniddos de la app. ¿Desesas cargar datos predeterminados?")
                .setNegativeButton("No"){ dialog, which ->
                    toast_manager("No se han cargado los datos.")
                }
                .setPositiveButton("Sí"){ dialog, which ->
                   loadAll()
                }.show()
    }

    fun loadAll(){
        val room = Room.databaseBuilder(this, DBContent::class.java, "Contenido").build()
        //toast_manager("Se han cargado los datos: ")
        lifecycleScope.launch {
            val content = Content("One Piece","Eichiro Oda","Toei Animation","Monkey D. Luffy es un muchacho que se hace a la mar para convertirse en pirata, y reunir una tripulación en su viaje de la búsqueda del One Piece, tesoro que quien lo encuentre se convertirá en el nuevo Rey de los Piratas.",1054,"DOMINGO" )
            val content2 = Content("Vinland Saga","Makoto Yukimura","Wit Studio","Basado en el personaje histórico de Thorfinn Karlsefni, Thorfinn es un guerrero de la compañía de Askeladd, a quien odia por haber matado a su padre y juró asesinarlo en un duelo.",24 , "SABADO")
            val content3 = Content("Sonny Boy","Shingo Natsume","Madhouse","Durante las vacaciones de verano 36 estudiantes y el edificio de su escuela fueron repentinamente transportados a otra dimensión similar al vacío.",12 ,"LUNES")

            val list = arrayOf(content,content2,content3)
            var i =0

            while(i<list.size) {
                val prueba = list.get(i)
                val content= room.daoContent().getByName(prueba.name)
                notiText += (content.name+". ")
                if (content==null)
                    room.daoContent().insert(prueba)
                i=i.inc()
            }
            var lista = room.daoContent().getContents()
            toast_manager("Se han cargado este numero de datos: "+lista.size.toString())
            sendNotification()
        }
    }
}