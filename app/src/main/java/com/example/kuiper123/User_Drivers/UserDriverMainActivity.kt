package com.example.kuiper123.User_Drivers

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kuiper123.MapsActivity
import com.example.kuiper123.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.jar.Manifest

class UserDriverMainActivity : AppCompatActivity() {

    lateinit var btn_Route : Button
    lateinit var freigth: EditText
    lateinit var fromRoute: EditText
    lateinit var toRoute: EditText
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private   var MY_PERMISION_REQUEST_ACCES_FINE_LOCATION :Int =0

    private lateinit var dbReference : DatabaseReference
    private lateinit var database : FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var dbReferenceUser : DatabaseReference






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_driver_main)

        btn_Route = findViewById(R.id.btn_Route)
        freigth = findViewById(R.id.eFreigthDriver)
        fromRoute = findViewById(R.id.eFromDriver)
        toRoute = findViewById(R.id.eToDriver)


        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.reference.child("Driver")
        dbReferenceUser=database.reference.child("User")


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),MY_PERMISION_REQUEST_ACCES_FINE_LOCATION)
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener{location:Location? ->

                val user = auth.currentUser

                if (location!=null) {
                    val driverLocation = dbReference.child(user!!.uid)

                    driverLocation.child("Latitude").setValue(location.latitude.toString())
                    driverLocation.child("Longitud").setValue(location.longitude.toString())



                    Log.d("Georef","latitude: ${location.latitude} and Longitud: ${location.longitude}"

                    )
                }

            }


        getLocation()


        btn_Route.setOnClickListener{
            startActivity(Intent(this,MapsActivity::class.java))
        }
    }

    private fun getLocation(){

        val user = auth.currentUser

        if (user!=null){



            dbReference.child(user.uid).addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(dataSnapshot : DataSnapshot) {
                    if (dataSnapshot.exists()){
                        val Lat = dataSnapshot.child("Latitude").getValue().toString()
                        val Lng = dataSnapshot.child("Longitud").getValue().toString()

                        Log.d("Coordenada","Coordenadas  user in "+Lat+" " + Lng)

                        getData(Lat,Lng)

                    }
                }
            })




        }else
        {
            Toast.makeText(this,"User Doest Exist", Toast.LENGTH_LONG).show()
        }

    }


    private fun getData(lat:String,lng:String){
        Log.d("Coordenada","Coordenadas  user "+lat+" " + lng)
    }
}
