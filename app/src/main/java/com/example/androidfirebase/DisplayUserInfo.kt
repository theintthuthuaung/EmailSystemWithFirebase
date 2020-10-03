package com.example.androidfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DisplayUserInfo : AppCompatActivity() {

    private var firstName:TextView? = null
    private var lastName:TextView? = null
    private var userName:TextView? = null
    private var firebaseAuth:FirebaseAuth? = null
    private var firebaseDatabase:DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_user_info)

        firstName = findViewById(R.id.displayFirstName_tv)
        lastName = findViewById(R.id.displayLastName_tv)
        userName = findViewById(R.id.displayUserName_tv)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseAuth?.currentUser!!.uid)

        firebaseDatabase?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(task: DataSnapshot) {
                if (task.exists()) {
                    val firstname = task.child("firstName").value as String
                    var lastname = task.child("lastName").value as String
                    var username = task .child("userName").value as String

                    firstName?.text = firstname
                    lastName?.text = lastname
                    userName?.text = username
                }
            }

        })
    }

    public fun update (view : View) {

        startActivity(Intent(this@DisplayUserInfo , ProfileActivity::class.java))
    }

    public fun change (view : View) {

        startActivity(Intent(this@DisplayUserInfo , ChangeEmail::class.java))
    }
 }