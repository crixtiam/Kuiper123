package com.example.kuiper123

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.ActivityNavigator
import com.example.kuiper123.Adapter_View.VehiclesAdapter
import com.example.kuiper123.Model.Vehicles
import com.example.kuiper123.Model.mapsMark
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.io.IOException
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap:GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    private lateinit var dbReference : DatabaseReference
    private lateinit var database : FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private   var MY_PERMISION_REQUEST_ACCES_FINE_LOCATION :Int =0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setUpApp()


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled


        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        //newCardinalPoint()
        // val Destino = LatLng()
        // placeMarkerOnMap()


    }

    private fun setUpApp(){

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        //val user = auth.currentUser
        dbReference = database.reference.child("Driver")
        //val mapsData = ArrayList<mapsMark>()


        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),MY_PERMISION_REQUEST_ACCES_FINE_LOCATION)
        }

        //mMap.isMyLocationEnabled = true


        fusedLocationClient.lastLocation
            .addOnSuccessListener{location: Location? ->

                val user = auth.currentUser

                if (location!=null) {

                    val driverLocation = dbReference.child(user!!.uid)
                    driverLocation.child("Ubication").child("Latitude").setValue(location.latitude.toString())
                    driverLocation.child("Ubication").child("Longitud").setValue(location.longitude.toString())

                    Log.d("Georef","latitude: ${location.latitude} and Longitud: ${location.longitude}")


                    lastLocation = location
                    var  currentLatLng = LatLng(lastLocation.latitude,lastLocation.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,15f))
                    placeMarkerOnMap(currentLatLng)


                }

            }

    }

    private fun placeMarkerOnMap(location: LatLng){
        val markerOptions = MarkerOptions().position(location)
        val titleStr = getAdress(location)
        markerOptions.title(titleStr)
        mMap.addMarker(markerOptions)
    }

    private fun getAdress(latLng: LatLng):String{

        val geocoder = Geocoder(this,Locale.getDefault())
        var addresses : List<Address>
        val address:Address
        var addressText:String=""

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude,1)
            if (null!=addresses&&!addresses.isEmpty()){
                address = addresses.get(0)
                Log.d("direcc",address.toString())
                for (i in 0 downTo address.maxAddressLineIndex step 1){
                    addressText += if(i==0) address.getAddressLine(i) else "\n"+address.getAddressLine(i)
                }
            }
        }catch (e:IOException){
         Log.e("MapsActivity",e.localizedMessage)
        }
        return addressText
    }


    private fun timerCounter(){
        val timer = object :CountDownTimer(20000,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
            }

        }
        timer.start()
    }







    }//final all



