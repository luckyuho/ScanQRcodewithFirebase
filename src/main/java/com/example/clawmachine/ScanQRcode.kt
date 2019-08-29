package com.example.clawmachine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.scanqrcodelayout.*


class ScanQRcode : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scanqrcodelayout)

        show_scan_btn()
        btn_scanQRcode.setOnClickListener {
            initFunc()
        }
    }

    private  fun initFunc(){
        val scanner = IntentIntegrator(this)
        scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        scanner.setBeepEnabled(false)
        scanner.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        // check if result scan any qr
        if (result != null){
            if (result.contents ==null) {
                // the result data is null or empty
                Toast.makeText(this, "Couldn't find QR-code", Toast.LENGTH_LONG).show()
            }else{
                val intent = Intent(this, ConfirmActivity::class.java)
                var bundle = Bundle()
                bundle.putString("website", result.contents.toString())
                intent.putExtra("toConfirmActivity", bundle)
                startActivity(intent)
            }
        }else {
            // the camera will not close if the result is still null
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun show_scan_btn(){
        btn_scanQRcode.visibility = View.VISIBLE
        btn_confirm.visibility = View.GONE
    }

    private fun show_confirm_btn(){
        btn_confirm.visibility = View.VISIBLE
        btn_scanQRcode.visibility = View.GONE
        val userEmail = FirebaseAuth.getInstance().currentUser!!.email
        var database: DatabaseReference = FirebaseDatabase.getInstance().reference
        database.child("Woody").child("users").setValue(userEmail)
    }

}
