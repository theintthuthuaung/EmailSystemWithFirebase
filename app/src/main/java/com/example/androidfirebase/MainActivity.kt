package com.example.androidfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private var signUpBTN:Button? = null
    private var email_et:EditText? = null
    private var password_et:EditText? = null
    private var firebaseAuth:FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signUpBTN = findViewById(R.id.signUp_btn)
        email_et = findViewById(R.id.emal_et)
        password_et = findViewById(R.id.pwd_et)
        firebaseAuth = FirebaseAuth.getInstance()

        signUpBTN?.setOnClickListener {
            registerNewUser()

        }
    }

    private fun registerNewUser() {
        val email_txt = email_et?.text.toString().trim()
        val pwd_txt = password_et?.text.toString().trim()

        if(TextUtils.isEmpty(email_txt ) && TextUtils.isEmpty(pwd_txt)){
            Toast.makeText(applicationContext , "This can't be empty!!" , Toast.LENGTH_SHORT).show()
        }
        else {
            firebaseAuth?.createUserWithEmailAndPassword(email_txt , pwd_txt)?.addOnCompleteListener(object:OnCompleteListener<AuthResult>{
                override fun onComplete(task: Task<AuthResult>) {
                    if(task.isSuccessful){
                        Toast.makeText(applicationContext , "Account Created." , Toast.LENGTH_SHORT).show()

                        val user:FirebaseUser = firebaseAuth!!.currentUser!!
                        user.sendEmailVerification().addOnCompleteListener(object : OnCompleteListener<Void> {
                            override fun onComplete(task: Task<Void>) {
                                if (task.isSuccessful){
                                    Toast.makeText(applicationContext , "Check your email to verify!" , Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@MainActivity , LogInActivity::class.java))
                                }
                                else {
                                    val error = task.exception?.message
                                    Toast.makeText(applicationContext , "Error " + error , Toast.LENGTH_SHORT).show()
                                }
                            }

                        })
                    }
                    else {
                        val error = task.exception?.message
                        Toast.makeText(applicationContext , "Error " + error , Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }

//    public fun Reset(view : View){
//        startActivity(Intent(this@MainActivity , ResetPasswordActivity::class.java))
//    }

    public fun LogIn (view: View) {
        startActivity(Intent(this@MainActivity , LogInActivity::class.java))
    }
}