package com.example.kuiper123

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.Year

class NewVehicleActivity : AppCompatActivity() {

    private lateinit var eModel: EditText
    private lateinit var eYear: EditText
    private lateinit var eMakeVeh: EditText
    private lateinit var eColor: EditText
    private lateinit var eLicencePlate: EditText
    private lateinit var eDriverName: EditText
    private lateinit var eDriverLicence: EditText
    private lateinit var eEmailDriver: EditText
    private lateinit var eMobile: EditText
    private lateinit var ePassword: EditText
    private lateinit var eVinVehicle : EditText
    private lateinit var eTypeVehicle : EditText
    private lateinit var identtityUser: String

    private lateinit var btnAdd: Button



    private lateinit var dbReference : DatabaseReference
    private lateinit var dbReferenceDriver : DatabaseReference
    private lateinit var database : FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_vehicle)

        eModel= findViewById(R.id.modelAdd)
        eYear= findViewById(R.id.yearVehicleAdd)
        eMakeVeh = findViewById(R.id.makeVehicleAdd)
        eColor = findViewById(R.id.colorVehicleAdd)
        eLicencePlate  = findViewById(R.id.licencePlateVehicleAdd)
        eDriverName = findViewById(R.id.txtDriverNameAdd)
        eDriverLicence = findViewById(R.id.txtDriverLicenceAdd)
        eEmailDriver = findViewById(R.id.txtEmailAdd)
        eMobile  =  findViewById(R.id.txtMobileAdd)
        ePassword = findViewById(R.id.txtPasswordAdd)
        eTypeVehicle = findViewById(R.id.typeVehicleAdd)
        eVinVehicle = findViewById(R.id.vinAdd)

        btnAdd = findViewById(R.id.btn_addNewVehicle)


        btnAdd.setOnClickListener{
            createNewVehicle()
        }





    }

    private fun createNewVehicle(){

        val model = eModel.text.toString()
        val year =  eYear.text.toString()
        val makeV = eMakeVeh.text.toString()
        val color = eColor.text.toString()
        val licencePlate  = eLicencePlate.text.toString()
        val driverName = eDriverName.text.toString()
        val driverLicence = eDriverLicence.text.toString()
        val EmailDriver  = eEmailDriver.text.toString()
        val mobile = eMobile.text.toString()
        val passwordDriver = ePassword.text.toString()
        val typeVehicle  = eTypeVehicle.text.toString()
        val VinVehicle = eVinVehicle.text.toString()

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.reference.child("User")
        //Key Usuario
        identtityUser = auth.currentUser.toString()



        if (!TextUtils.isEmpty(model)&& !TextUtils.isEmpty(year)&&!TextUtils.isEmpty(makeV)&&!TextUtils.isEmpty(color)&&!TextUtils.isEmpty(licencePlate)&&!TextUtils.isEmpty(driverLicence)&&!TextUtils.isEmpty(driverName)&&!TextUtils.isEmpty(EmailDriver)&&!TextUtils.isEmpty(mobile)&&!TextUtils.isEmpty(passwordDriver)&&!TextUtils.isEmpty(typeVehicle)&&!TextUtils.isEmpty(VinVehicle))
        {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Do you want Add this New Vehicle")
            builder.setPositiveButton(android.R.string.yes){dialog, which ->

                addVehicle(model,year,makeV,color,licencePlate,typeVehicle,VinVehicle)
                addDriver(driverName,driverLicence,EmailDriver,mobile,VinVehicle)
                createAccountDriver(passwordDriver,EmailDriver,auth.currentUser.toString(),VinVehicle)
            }

            builder.setNegativeButton(android.R.string.no){dialog, which ->
                Toast.makeText(this,"You cancel to add new Vehicle to the  list  Vehicle",Toast.LENGTH_LONG).show()
            }

            builder.show()
        }
        else
        {
            Toast.makeText(this,"Please fill the blank Space",Toast.LENGTH_LONG).show()
        }

    }

    private fun addVehicle( model:String, year:String, makeV : String, color:String, licencePlate : String, typeVehicle : String, VinVehicle: String){

        val user = auth.currentUser
        val referenceBD  = dbReference.child(user!!.uid)
        val newVehicleBD= referenceBD.child("Vehicles")
        val newVehicleReferenceBD = newVehicleBD.child(VinVehicle)

        //val newVehicleReferenceBD =referenceBD.child("Vehicles")
        newVehicleReferenceBD.child("Model").setValue(model)
        newVehicleReferenceBD.child("Year").setValue(year)
        newVehicleReferenceBD.child("Make").setValue(makeV)
        newVehicleReferenceBD.child("Color").setValue(color)
        newVehicleReferenceBD.child("Licence_Plate").setValue(licencePlate)
        newVehicleReferenceBD.child("Type").setValue(typeVehicle)
        newVehicleReferenceBD.child("VIN").setValue(VinVehicle)

        newVehicleReferenceBD.child("ReferenceVM")
    }

    private fun addDriver(driverName:String,driverLicence:String,emailDriver:String,mobileDriver:String,VINVM:String ){

        val user = auth.currentUser
        val referenceBD  = dbReference.child(user!!.uid)
        val newDriverBD= referenceBD.child("Drivers")
        val newDriverReferenceBD = newDriverBD.child(VINVM)

        newDriverReferenceBD.child("Driver_Name").setValue(driverName)
        newDriverReferenceBD.child("Driver_Licence").setValue(driverLicence)
        newDriverReferenceBD.child("Driver_Email").setValue(emailDriver)
        newDriverReferenceBD.child("Driver_Mobile").setValue(mobileDriver)
        newDriverReferenceBD.child("VIN").setValue(VINVM)
        newDriverReferenceBD.child("Status").setValue("KO")
        newDriverReferenceBD.child("UserKeyIdent").setValue(identtityUser)


    }

    private fun createAccountDriver(passwordDriver:String,emailDriver: String,keyOwner:String,VIN:String){

        database = FirebaseDatabase.getInstance()
       // auth = FirebaseAuth.getInstance()

        dbReferenceDriver = database.reference.child("Driver")

        //Owner
        dbReference = database.reference.child("User")



        auth.createUserWithEmailAndPassword(emailDriver,passwordDriver)
            .addOnCompleteListener(this){
                    task ->
                // verify register OK
                if (task.isComplete){

                    val userDriver:FirebaseUser?=auth.currentUser
                    val driverBD = dbReferenceDriver.child(userDriver!!.uid)
                    driverBD.child("Email").setValue(emailDriver)
                    driverBD.child("VIN").setValue(VIN)
                    driverBD.child("Status").setValue("KO")




                }
            }
    }
}
