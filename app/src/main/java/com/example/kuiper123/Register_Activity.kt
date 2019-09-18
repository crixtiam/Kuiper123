package com.example.kuiper123

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register_Activity : AppCompatActivity() {

    private lateinit var userNameRegister:EditText
    private lateinit var passwordRegister1:EditText
    private lateinit var rePasswordRegister:EditText
    private lateinit var emailRegister:EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userNameRegister=findViewById(R.id.txtUserRegister)
        passwordRegister1 = findViewById(R.id.txtPasswordRegister)
        rePasswordRegister = findViewById(R.id.txtRePasswordRegister)
        emailRegister = findViewById(R.id.txtEmailRegister)

        progressBar= findViewById(R.id.tvProgressBarReg)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.reference.child("User")


    }

    fun onClickPressedRegister(view:View){

        createNewAccount()
    }

    private fun createNewAccount(){

        val nameUserApp:String = userNameRegister.text.toString()
        val passwordApp = passwordRegister1.text.toString()
        val rePasswordApp = rePasswordRegister.text.toString()
        val emailApp = emailRegister.text.toString()

        //val myRef = database.getReference("message")
        //myRef.setValue("Hello, World!")


        if (!TextUtils.isEmpty(nameUserApp) && !TextUtils.isEmpty(passwordApp) && !TextUtils.isEmpty(rePasswordApp) && !TextUtils.isEmpty(emailApp) ){

            progressBar.visibility=View.VISIBLE

            auth.createUserWithEmailAndPassword(emailApp,passwordApp)
                .addOnCompleteListener(this){
                    task ->
                    // verify register OK
                    if (task.isComplete){
                        //get user  register
                        val user:FirebaseUser?=auth.currentUser
                        verifyEmail(user)

                        val database = FirebaseDatabase.getInstance()
                        val myRef = database.getReference("message")
                        myRef.setValue("Hello, World!")


                        //val userBD =dbReference.child(user?.uid!!)
                        //userBD.child("Username").setValue(nameUserApp)
                        action()
                    }
                }

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
