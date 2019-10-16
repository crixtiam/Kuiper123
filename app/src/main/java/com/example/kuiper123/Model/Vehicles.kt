package com.example.kuiper123.Model

import com.google.firebase.database.DataSnapshot
import java.lang.Exception

class Vehicles {

     var Model : String?= null
     var Year:  String?= null
     var Make : String?= null
     var Color : String?= null
     var Type : String?= null
     var Licence_Plate :  String?= null
     var VIN : String?= null

    constructor(){}


    // lateinit var referenceVM: String

   /* init {
        try {
            val data : HashMap<String,Any> = snapshot.value as HashMap<String, Any>

            // pendiente snapshot.key
            model = data ["Model"] as String
            year = data  ["Year"] as String
            makeVehicle = data ["Make"] as String
            colorVehicle = data["color"] as String
            typeVehicle  = data  ["Type"] as String
            licencePlate = data ["Licence_Plate"] as String
            VinVehicle = data["VIN"] as String


        }catch (e:Exception){
            e.printStackTrace()
        }
    }*/

  /*  private fun referenceVehicles(referenceVMIn : String){
        referenceVM = referenceVMIn
    }*/

}