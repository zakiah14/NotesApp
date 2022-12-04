package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    var btn_register : Button? =null
   var   mAuth :FirebaseAuth?= null
    var edit_email :EditText ?= null
    var edit_password :EditText ?= null
    var progressBar :ProgressBar ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        connectedViews ()
        mAuth = FirebaseAuth.getInstance()




        btn_register?.setOnClickListener {
           register()
        }
}
 private fun  connectedViews (){

     btn_register =findViewById(R.id.btn_register)
     edit_email =findViewById(R.id.edit_name)
     edit_password =findViewById(R.id.edit_password)
     progressBar =findViewById(R.id.progressBar_signup)

 }
    private fun register () {

        var email= edit_email?.text.toString()
        var password = edit_password?.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty()) {
            progressBar?.visibility=View.VISIBLE

            mAuth?.createUserWithEmailAndPassword(email, password)
                //لتحقق إذا تمت عملية التسجيل لو لا بوجود نت؟
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        progressBar?.visibility=View.GONE
                        sendEmailVerfiycaion()
//                        Toast.makeText(applicationContext, "Successful", Toast.LENGTH_LONG)
//                            .show()
//                        var intent_ToLogin = Intent(this@Register,login::class.java)
//                        startActivity(intent_ToLogin )
                    } else {

                        Toast.makeText(
                            applicationContext,
                            it.exception.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        } else{

            Toast.makeText(applicationContext, "pleas enter the email or password  ", Toast.LENGTH_LONG)
                .show()}



    }
    private  fun sendEmailVerfiycaion(){
        val user =mAuth?.currentUser
user?.sendEmailVerification()?.addOnCompleteListener {
   if(it.isSuccessful){
       val intent_ToLogin = Intent(this@Register,login::class.java)
       startActivity(intent_ToLogin )


   }else{
       Toast.makeText(applicationContext,it.exception.toString(),Toast.LENGTH_LONG).show()
   }


}

    }

}
