package com.example.kuiper123

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register_Activity : AppCompatActivity() {

    private lateinit var userNameRegister:EditText
    private lateinit var passwordRegister:EditText
    private lateinit var mobileRegister:EditText
    private lateinit var emailRegister:EditText
    private lateinit var checkBoxCarRegister: CheckBox
    private lateinit var btnRegister:Button

    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth

    private var status : String ="KO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userNameRegister=findViewById(R.id.txtUserRegister)
        passwordRegister = findViewById(R.id.txtPasswordRegister)
        mobileRegister = findViewById(R.id.txtMobileRegister)
        emailRegister = findViewById(R.id.txtEmailRegister)
        checkBoxCarRegister = findViewById(R.id.checkboxCarReg)
        btnRegister = findViewById(R.id.btn_register)

        progressBar= findViewById(R.id.tvProgressBarReg)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.reference.child("User")


        btnRegister.setOnClickListener{
            createNewAccount()
        }


    }



    private fun createNewAccount(){

        val nameUserApp:String = userNameRegister.text.toString()
        val passwordApp = passwordRegister.text.toString()
        val mobileApp = mobileRegister.text.toString()
        val emailApp = emailRegister.text.toString()
        var boolean = false



        if (checkBoxCarRegister.isChecked){
            boolean = true
            status="OK"
        }
        else{
            boolean =false
            Toast.makeText(this,"Please Check that you are owner at least one car",Toast.LENGTH_LONG).show()
        }


        if (!TextUtils.isEmpty(nameUserApp) && !TextUtils.isEmpty(passwordApp) && !TextUtils.isEmpty(mobileApp) && !TextUtils.isEmpty(emailApp)&&boolean&&(status=="OK") ){

            progressBar.visibility=View.VISIBLE

            auth.createUserWithEmailAndPassword(emailApp,passwordApp)
                .addOnCompleteListener(this){
                    task ->
                    // verify register OK
                    if (task.isComplete){

                        val user:FirebaseUser?=auth.currentUser
                        verifyEmail(user)

                        val userBD = dbReference.child(user!!.uid).child("Personal_data")
                        userBD.child("Name").setValue(nameUserApp)
                        userBD.child("Mobile").setValue(mobileApp)
                        userBD.child("Mail").setValue(emailApp)
                        userBD.child("Status").setValue(status)

                        action()
                    }
                }

        }
        else{
            Toast.makeText(this,"Please Fill the Blank Space",Toast.LENGTH_LONG).show()
        }
    }

    private fun action(){

        startActivity(Intent(this,Login_Activity::class.java))
    }

    //send  email to user to verify registration OK
    private fun verifyEmail(user: FirebaseUser?){

        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                task ->
                if (task.isComplete){
                    Toast.makeText(this,"Email send",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"Warning Email no send",Toast.LENGTH_LONG).show()
                }
            }
    }

}
