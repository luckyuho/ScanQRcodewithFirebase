package com.example.clawmachine

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.loginlayout.*
import kotlinx.android.synthetic.main.registerlayout.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginlayout)

        // Login Button
        login.setOnClickListener {

            val intent = Intent(this, ScanQRcode::class.java)
//            startActivity(intent)
            firebase_authenticateUser(login_email.text.toString(), login_password.text.toString(), intent)

        }

        // Register Text
        register.setOnClickListener {

            showRegistration()

            // Save new user information
            save.setOnClickListener {
                firebase_createNewUser(register_email.text.toString(), register_password.text.toString())
            }

            // Login Text
            Login_to_home.setOnClickListener {
                showHome()
            }
        }

    }

    private fun showRegistration() {
        registration_layout.visibility = View.VISIBLE
        home_l1.visibility = View.GONE
    }

    private fun showHome() {
        registration_layout.visibility = View.GONE
        home_l1.visibility = View.VISIBLE
    }

    // Firebase register
    private fun firebase_createNewUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username, email and password!", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener

            Toast.makeText(this, "Register Success!", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(this, "Failed: ${it.message}", Toast.LENGTH_SHORT).show()
        }

    }

    // Firebase login
    private fun firebase_authenticateUser(email: String, password: String, intent: Intent) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password!", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener

            Toast.makeText(this, "Login Success!", Toast.LENGTH_SHORT).show()
            startActivity(intent)

        }.addOnFailureListener {
            Toast.makeText(this, "Failed: ${it.message}", Toast.LENGTH_SHORT).show()
        }

    }
}
