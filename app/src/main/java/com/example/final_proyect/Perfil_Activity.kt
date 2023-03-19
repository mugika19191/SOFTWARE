package com.example.final_proyect

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Perfil_Activity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil)
/*
        val fragment = secondFragment()
        val bundle = Bundle()
        bundle.putString("name",intent.getStringExtra("NAME").toString())
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragment,fragment).commit()
*/
        val botmenu = findViewById<BottomNavigationView>(R.id.navigation)
        val controller = findNavController(R.id.fragment)
        botmenu.setupWithNavController(controller)






        /*val bundle = Bundle(intent.getStringExtra("NAME").toString(), ++)
        bundle.putString()
        val trans = supportFragmentManager.beginTransaction()
        val frag = secondFragment()
        trans.replace(R.id.fragment, frag )
        trans.addToBackStack(null)
        trans.commit()
        var text =
*/
        //val btn_click_me = findViewById<ImageButton>(R.id.options)

        /*btn_click_me.setOnClickListener {
            select_image()
        }*/
    }

    fun prepare_user(name:String){
        val nombre = findViewById<TextView>(R.id.nombrePerfil)
        nombre.setText(name)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.MANAGE_MEDIA),1)
        val room = Room.databaseBuilder(this, DBPruebas::class.java, "Usuario").build()
        lifecycleScope.launch {

            var user=room.daoUsuario().getByName(name)
/*
            if (user.img!=""){

                image.setImageURI(user.img.toUri())
            }
            */
        }
    }
    fun updateUserImg(uriIMG : String){
        val room = Room.databaseBuilder(this, DBPruebas::class.java, "Usuario").build()
        val name = findViewById<TextView>(R.id.nombrePerfil)

        lifecycleScope.launch {

            var user=room.daoUsuario().getByName(name.text.toString())
            val newUser= Usuario(user.name,user.pass,uriIMG)
            room.daoUsuario().update(newUser)
        }
    }

    /*val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){uri->
        if (uri!=null){
            //hemos elegido imagen
            image.setImageURI(uri)
            updateUserImg(uri.toString())
        }
        else{
            // no hay imagen
        }
    }
    fun select_image(){
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }*/

}