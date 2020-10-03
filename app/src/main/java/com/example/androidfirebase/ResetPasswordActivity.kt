package com.example.androidfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {

    private var reEmail:EditText? = null
    private var reBTN:Button? = null
    private var firebaseAuth:FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        reEmail = findViewById(R.id.reset_email)
        reBTN = findViewById(R.id.signUp_btn)
        firebaseAuth = FirebaseAuth.getInstance()

        reBTN?.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        var email = reEmail?.text.toString().trim()

        if(TextUtils.isEmpty(email)){
            Toast.makeText(applicationContext , "Your email is empty!" , Toast.LENGTH_SHORT).show()
        }
        else {
            firebaseAuth?.sendPasswordResetEmail(email)?.addOnCompleteListener(object : OnCompleteListener<Void>{
                override fun onComplete(task: Task<Void>) {
                    if(task.isSuccessful){
                        Toast.makeText(applicationContext , "Check your Email." , Toast.LENGTH_SHORT).show()
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