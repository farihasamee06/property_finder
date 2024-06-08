package com.example.group_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.group_project.databinding.ActivityLoginScreenBinding
import com.example.group_project.databinding.ActivityPropertydescriptionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginScreen : AppCompatActivity() {
    lateinit var binding: ActivityLoginScreenBinding
    // create a Firebase auth variable
    private lateinit var auth:FirebaseAuth
    private val TAG:String = "TESTING"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= Firebase.auth

        binding.btnlogin.setOnClickListener {
            val emailFromUI=binding.tvemail.text.toString()
            val passwordfromUI=binding.tvpassword.text.toString()

            loginuser(emailFromUI,passwordfromUI)

        }
        binding.btnlogout.setOnClickListener {
            logoutCurrentUser()
        }
    }
    private fun loginuser(email:String,pass:String) {
        if (auth.currentUser != null) {
            binding.results.text = "THERE IS ALREADY USER LOGGED IN"
            return
        }
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    binding.results.text = "SUCCESS! ${email} logged in"
                    //binding.btnLogin.isEnabled = false


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    binding.results.text = "ERROR: Check LogCat for failure reason"
                }
            }
    }
                private fun logoutCurrentUser()
                {
                    if(auth.currentUser == null)
                    {
                        binding.results.text="Cannot log out, bceause there is no logged in user"
                        return
                    }
                    Firebase.auth.signOut()
                    binding.results.text="USER LOGGED OUT"
                    binding.tvemail.setText("")
                    binding.tvpassword.setText("")
                }
            }


