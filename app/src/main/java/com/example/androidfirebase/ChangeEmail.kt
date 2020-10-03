package com.example.androidfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ChangeEmail : AppCompatActivity() {

    private var email_et:EditText? = null
    private var pwd_et:EditText? = null
    private var newEmail_et:EditText? = null
    private var changeBtn:Button? = null
    private var firebaseAuth:FirebaseAuth? = null
    private var user:FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_email)

        email_et = findViewById(R.id.changeemail_et)
        pwd_et = findViewById(R.id.changePwd_et)
        newEmail_et = findViewById(R.id.changeNewemail_et)
        changeBtn = findViewById(R.id.chaneEmail_btn)
        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth!!.currentUser

        changeBtn?.setOnClickListener {

            changeEmailAddress()
        }
    }

    private fun changeEmailAddress() {
        var email_txt = email_et?.text.toString().trim()
        var pwd_txt = pwd_et?.text.toString().trim()
        var newEmail_txt = newEmail_et?.text.toString().trim()

        var userInfo = EmailAuthProvider.getCredential(email_txt , pwd_txt)
        user?.reauthenticate(userInfo)?.addOnCompleteListener(object : OnCompleteListener<Void>{
            override fun onComplete(p0: Task<Void>) {

                user!!.updateEmail(newEmail_txt)!!
                    .addOnCompleteListener(object : OnCompleteListener<Void> {
                        override fun onComplete(task: Task<Void>) {

                            if (task.isSuccessful) {

                                Toast.makeText(
                                    applicationContext,
                                    "Your email has been successfully updated!",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else {

                                val error = task.exception?.message
                                Toast.makeText(
                                    applicationContext,
                                    "Error " + error,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
            }
        })
    }
}