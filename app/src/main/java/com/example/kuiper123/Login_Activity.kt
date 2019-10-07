package com.example.kuiper123

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class Login_Activity : AppCompatActivity() {

    private lateinit var dbReference: DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    private lateinit var btnLogin :Button
    private lateinit var eUsername : EditText
    private lateinit var ePassword : EditText
 //   private lateinit var callbackManager: CallbackManager

    private lateinit var txtMail:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth= FirebaseAuth.getInstance()
        /*callbackManager = CallbackManager.Factory.create()
        txtMail = findViewById(R.id.txtForgotPassword)

        btn_loginFacebook.setPublishPermissions("email")
        btn_loginFacebook.setOnClickListener{
            onClickPressedLoginFacebook()
        }*/

        //printKeyHash()
        btnLogin=findViewById(R.id.tv_submitLogin)
        eUsername = findViewById(R.id.UserNameInput)
        ePassword = findViewById(R.id.PasswordInput)


        btnLogin.setOnClickListener{
            onClickStartApp()
        }

    }


    fun onClickLogintxt(view: View){
        startActivity(Intent(this,Register_Activity::class.java))
    }

    private fun onClickStartApp(){

        val password = ePassword.text.toString()
        val email = eUsername.text.toString()

        if (!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(email)){
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful){
                        action()
                        finish()
                    }
                    else{
                        Toast.makeText(this,"Authentication Failed",Toast.LENGTH_LONG).show()
                    }
                }

        }
        else{
            Toast.makeText(this,"Please Fill the Blank Space",Toast.LENGTH_LONG).show()
        }


    }



    private fun action(){
        startActivity(Intent(this,MainViewActivity::class.java))
    }

  /*  fun onClickPressedForgot(view: View){
        val email = txtMail.text.toString()
//corregir
        if (!TextUtils.isEmpty(email)){
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this){
                    task ->
                    if (task.isSuccessful){

                    }

                    else
                    {

                    }
                }
        }
    }*/


   /* private fun printKeyHash(){
        try {
            val info  = packageManager.getPackageInfo("com.example.kuiper123",PackageManager.GET_SIGNATURES)
            for (signature in info.signatures){
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KEYHASH",Base64.encodeToString(md.digest(),Base64.DEFAULT))
            }
        }
        catch (e:PackageManager.NameNotFoundException){

        }

        catch (e:NoSuchAlgorithmException){

        }
    }*/

/*
    private fun onClickPressedLoginFacebook(){
        btn_loginFacebook.registerCallback(callbackManager, object :FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                handleFacebookAccessToken(result!!.accessToken)
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException?) {

            }
        })
    }
*/

/*
    private fun handleFacebookAccessToken(accessToken: AccessToken?){
        val credential=FacebookAuthProvider.getCredential(accessToken!!.token)
        auth.signInWithCredential(credential)
            .addOnFailureListener{
                task->
                Toast.makeText(this,task.message,Toast.LENGTH_LONG).show()
            }
            .addOnSuccessListener {
                result->
                val email = result.user?.email
                Toast.makeText(this,"You logged with email" + email,Toast.LENGTH_LONG).show()

            }
    }
*/

/*    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode,resultCode,data)
    }*/
}
