package com.example.socialx


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth




class Signup : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    var emailPattern : String = "\"[a-zA-Z0-9\\\\+\\\\.\\\\_\\\\%\\\\-\\\\+]{1,256}\""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var view : View = inflater.inflate(R.layout.fragment_signup, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

//      ======================== if not account then RESISTER ===================================

        val resister : TextView = view.findViewById(R.id.loginIfAccountExist)
        resister.setOnClickListener {
            val fragmentB = Login()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragmentB, "fragmentId")
                .commit();
        }

//        ======================= for input fields validation ==================

        var name : EditText = view.findViewById(R.id.name)
        var email : EditText = view.findViewById(R.id.email_create)
        var phone : EditText = view.findViewById(R.id.phone)
        var pass : EditText = view.findViewById(R.id.password_login)
        val check : CheckBox = view.findViewById(R.id.termsConditions)

        val resister_btn : TextView = view.findViewById(R.id.resister_btn)

        resister_btn.setOnClickListener {
            if(name.length()<2){
                name.error = "First name is required!"
            }
            else if(email.length()<6){
                email.error = "Email required!"
            }
            else if(phone.length()<5){
                phone.error = "contact number isrequired!"
            }
            else if(pass.length()<6){
                pass.error = "Password length should be be more then 6"
            }
            else if(!check.isChecked){
                Toast.makeText(activity, "T&C should be checked!", Toast.LENGTH_SHORT).show()
            }
            else{

//      ============== Singing In with Email and Password ====================
                firebaseAuth.createUserWithEmailAndPassword(email.text.toString(), pass.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(activity, "Resistration Successfull!!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(activity, Home::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(activity, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return view
    }

}
