package com.example.androidfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LogInActivity : AppCompatActivity() {

    private var loginBTN:Button? = null
    private var emailEt:EditText? = null
    private var pwdEt:EditText? = null
    private var firebaseAuth:FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        emailEt = findViewById(R.id.emal_et_login)
        pwdEt = findViewById(R.id.pwd_et_login)
        loginBTN = findViewById(R.id.login_btn_login)
        firebaseAuth = FirebaseAuth.getInstance()

        loginBTN?.setOnClickListener {
            loginAccount()
        }
    }

    private fun loginAccount() {

        var email_txt = emailEt?.text.toString().trim()
        var pwd_txt = pwdEt?.text.toString().trim()

        if (TextUtils.isEmpty(email_txt) || TextUtils.isEmpty(pwd_txt)) {

            Toast.makeText(applicationContext , "This Field can't be empty!!" , Toast.LENGTH_SHORT).show()
        }

        else {

            firebaseAuth?.signInWithEmailAndPassword(email_txt , pwd_txt)?.addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                override fun onComplete(task: Task<AuthResult>) {

                    if(task.isSuccessful){

                        Toast.makeText(applicationContext , "Login Successfull!" , Toast.LENGTH_SHORT).show()

                        val user: FirebaseUser = firebaseAuth!!.currentUser!!
                        if(user.isEmailVerified){

                            startActivity(Intent(this@LogInActivity , TestActivity::class.java))
                        }

                        else {

                            Toast.makeText(applicationContext , "Your Account is not verified!" , Toast.LENGTH_SHORT).show()
                        }
                    }

                    else {

                        val error = task.exception?.message
                        Toast.makeText(applicationContext , "Error " + error , Toast.LENGTH_SHORT).show()
                    }
                }

            })

        }
    }
}