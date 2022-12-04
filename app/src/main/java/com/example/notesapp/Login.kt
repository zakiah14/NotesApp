package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
var btn_signUp : Button? =null
var   mAuth : FirebaseAuth?= null
var enter_email : EditText?= null
var enter_password : EditText?= null
var btn_login : Button? =null
class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        connectedViews ()
        mAuth =FirebaseAuth.getInstance()

        onStart()
        btn_login?.setOnClickListener {
            Login ()
    }
        btn_signUp?.setOnClickListener {
            val intent_sign = Intent(this@login,Register::class.java)
            startActivity(intent_sign)}}
    private fun  connectedViews (){

        enter_password =findViewById(R.id.enter_password)
        enter_email =findViewById(R.id.enter_name)
        btn_login =findViewById(R.id.btn_login)
        btn_signUp =findViewById(R.id.btn_signUp)

    }
    override fun onStart() {
       super.onStart()
      if(mAuth?.currentUser==null){

           val intentToLogin =Intent(this@login,Register::class.java)
            startActivity(intentToLogin)}}
    private fun Login (){


        var password= enter_password?.text.toString()
        var email = enter_email?.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty()){
            mAuth?.signInWithEmailAndPassword(email,password)?.addOnCompleteListener {

                if(it.isSuccessful){
                    verifyEmailAddress()
                   }
                else {

                    Toast.makeText(
                        applicationContext,
                        it.exception.toString(),
                        Toast.LENGTH_LONG
                    ).show()}}

        }else{
            Toast.makeText(applicationContext, "pleas enter the email or password  ", Toast.LENGTH_LONG)
                .show()}

    }
private  fun  verifyEmailAddress(){

    val user= mAuth?.currentUser
    if(user !!.isEmailVerified){
        val intentToMainActivity =Intent(this@login,MainActivity ::class.java)
        startActivity(intentToMainActivity)

}else{Toast.makeText(applicationContext,"Pleas  verify your email",Toast.LENGTH_LONG) .show()}
}}
