package com.example.androidfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    public fun changeEmail (view : View) {

        startActivity(Intent(this@TestActivity , ChangeEmail::class.java))
    }

    public fun profile (view : View) {

        startActivity(Intent(this@TestActivity , ProfileActivity::class.java))
    }

    public fun info (view : View) {

        startActivity(Intent(this@TestActivity , DisplayUserInfo::class.java))
    }

//    public fun upload (view : View) {
//
//        startActivity(Intent(this@TestActivity , UploadImage::class.java))
//    }
}