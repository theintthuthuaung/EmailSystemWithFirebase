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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {

    private var firstName:EditText? = null
    private var lastName:EditText? = null
    private var userName:EditText? = null
    private var applyBTN:Button? = null
    private var firebaseAuth:FirebaseAuth? = null
    private var firebaseDatabase:DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        firstName = findViewById(R.id.profile_firstName_et)
        lastName = findViewById(R.id.profile_lastName_et)
        userName = findViewById(R.id.profile_userName_et)
        applyBTN = findViewById(R.id.applyInfo_btn)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseAuth?.currentUser!!.uid)

        applyBTN?.setOnClickListener {
            saveUserInfo()
        }
    }

    private fun saveUserInfo() {

        var firstName_txt = firstName?.text.toString().trim()
        var lastName_txt = lastName?.text.toString().trim()
        var userName_txt = userName?.text.toString().trim()

        if(TextUtils.isEmpty(firstName_txt) || TextUtils.isEmpty(lastName_txt) || TextUtils.isEmpty(userName_txt)) {

            Toast.makeText(applicationContext , "This fields can't be empty!" , Toast.LENGTH_SHORT).show()
        }

        else {

            val userInfo = HashMap<String,Any>()
            userInfo.put("firstName",firstName_txt)
            userInfo.put("lastName",lastName_txt)
            userInfo.put("userName",userName_txt)

            firebaseDatabase?.updateChildren(userInfo)?.addOnCompleteListener(object : OnCompleteListener<Void>{
                override fun onComplete(task: Task<Void>) {

                    if(task.isSuccessful) {

                        Toast.makeText(applicationContext , "Your information is successfully applied." , Toast.LENGTH_SHORT).show()
                    } else {

                        val error = task.exception?.message
                        Toast.makeText(applicationContext , "Error " + error , Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }
}