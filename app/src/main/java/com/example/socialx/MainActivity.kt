package com.example.socialx

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var loginFragment = Login()
        var signupFragment = Signup()

        var mainContainer : BottomNavigationView = findViewById(R.id.containerLayout)

        supportFragmentManager.beginTransaction().replace(R.id.container, loginFragment).commit()

        mainContainer.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.login -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, loginFragment)
                        .commit()

                    return@OnItemSelectedListener true
                }
                R.id.signup -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, signupFragment)
                        .commit()
                    return@OnItemSelectedListener true
                }
            }
            false
        })
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        if(auth.currentUser!=null){
            var intent  = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }
}