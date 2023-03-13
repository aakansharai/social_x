package com.example.socialx

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.util.*


class Login : Fragment() {

    private lateinit var auth : FirebaseAuth
    private lateinit var googleClient : GoogleSignInClient

    lateinit var callbackManager : CallbackManager

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view : View =  inflater.inflate(R.layout.fragment_login, container, false)
        callbackManager = CallbackManager.Factory.create();

//  ======================= Singing In with Email and Password &&&  input fields validation ==================

        val emailLogin : EditText = view.findViewById(R.id.login_page_email)
        val password : EditText = view.findViewById(R.id.login_page_password)
        val loginBtn : TextView = view.findViewById(R.id.login_btn)

        loginBtn.setOnClickListener {
            if(emailLogin.length()<5){
                emailLogin.error = "Email required!"
            }
            else if(password.length()<6){
                password.error = "password must contain 6 letters!"
            }
            else{
                auth.signInWithEmailAndPassword(emailLogin.text.toString(), password.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(activity, "Logged In!", Toast.LENGTH_SHORT).show()
                        var intent = Intent(activity, Home::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(activity, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

// ======================== if not account then RESISTER ===================================
        val resister : TextView = view.findViewById(R.id.resisterNowIfAccountNotExist)
        resister.setOnClickListener {
            val fragmentB = Signup()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragmentB, "fragmentId")
                .commit();
        }

// =============== Login With Google ===============================================

        auth= FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleClient = GoogleSignIn.getClient(requireActivity(), gso)

        var login : ImageView = view.findViewById(R.id.google_icon)
        login.setOnClickListener {
            signinGoogle()
        }


// ============== Log In with Facebook ==========================
        var facebook : ImageView = view.findViewById(R.id.facebook_icon)
        facebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        }

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Toast.makeText(activity, "Logged In with Facebook successfully!", Toast.LENGTH_SHORT).show()
                    var intent = Intent(activity, Home::class.java)
                    startActivity(intent)
                }

                override fun onCancel() {
                    Toast.makeText(activity, "Logged In with Facebook Failed!", Toast.LENGTH_SHORT).show()
                }

                override fun onError(exception: FacebookException) {
                    Toast.makeText(activity, "Logged In with Facebook Failed!", Toast.LENGTH_SHORT).show()
                }
            })

//==================== MAIN CODE ENDS HERE =======================================
        return view

    }

    // FUNCTIONS USED IN MAIN CODE

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


    // LOGIN WITH GOOGLE FUNCTIONS
    private fun signinGoogle() {
        val signinIntent = googleClient.signInIntent
        launcher.launch(signinIntent)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){

            var account : GoogleSignInAccount = task.result
            if(account!=null){
                updateUI(account)
            }
        }
        else{
            Toast.makeText(activity, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {

        val credential  = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful){
                var intent = Intent(activity, Home::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(activity, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    //    LOGIN WITH GOOGLE FUNCTIONS - END

}