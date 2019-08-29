package com.example.clawmachine

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.scanqrcodelayout.*

class ConfirmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scanqrcodelayout)

        show_confirm_btn()

        val qrcodewebsite = intent.getBundleExtra("toConfirmActivity").getString("website")

        var boss = ""
        val user = FirebaseAuth.getInstance().currentUser
        var database: DatabaseReference = FirebaseDatabase.getInstance().reference
        Log.d("ConfirmActivity", "user: ${user!!.email.toString()}, uid: ${user.uid}")

        if (qrcodewebsite != null) {
            // boss woody
            if (qrcodewebsite == "https://woody.com") {
                boss = "woody"
                boss_img.setImageResource(R.drawable.woody)
            } else if (qrcodewebsite == "https://jessie.com") {
                boss = "jessie"
                boss_img.setImageResource(R.drawable.jessie)
            } else {
                // this error because result maybe empty so use settext
                tvresult.setText("Not yet available! " + qrcodewebsite)
            }

            btn_confirm.setOnClickListener {
                database.child( boss ).child("users").child(user.uid).setValue(user!!.email.toString())
            }
        }
    }

    private fun show_confirm_btn() {
        btn_confirm.visibility = View.VISIBLE
        btn_scanQRcode.visibility = View.GONE
    }
}