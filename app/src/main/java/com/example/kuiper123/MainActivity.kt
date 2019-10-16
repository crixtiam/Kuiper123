package com.example.kuiper123

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.kuiper123.User_Drivers.UserDriverMainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private  lateinit var valueStatus : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        database =  FirebaseDatabase.getInstance()
        dbReference =  database.reference.child("User").child(user?.uid.toString()).child("Personal_data")
        valueStatus = "null"

        if (user!=null){


            dbReference.addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(dataSnapshot : DataSnapshot) {
                    if (dataSnapshot.exists()){
                       val Status = dataSnapshot.child("Status").getValue().toString()
                        if(Status=="OK"){
                            getData(Status)

                        }

                        Log.d("Tag",Status)
                    }else{
                        getData("KO")
                        Log.d("Tag","KO")
                    }
                }
            })




        }else
        {
            Toast.makeText(this,"User Doest Exist",Toast.LENGTH_LONG).show()
        }
    }

    private fun getData(Status:String?){
       // verificationKey?.Status = Status
     //   Log.d("Tag_Fun",verificationKey?.Status.toString())
        Log.d("Tag_Status",Status)
        if (Status!=null) {

            when(Status){
                "OK"->{
                    startActivity(Intent(this, MainViewActivity::class.java))
                    finish()}

                "KO"->{
                    startActivity(Intent(this, UserDriverMainActivity::class.java))
                    finish()}
                else->{
                    Toast.makeText(this,"NULL",Toast.LENGTH_LONG).show()}


            }


        }else{
            Toast.makeText(this,"NULL",Toast.LENGTH_LONG).show()}

      }

}
