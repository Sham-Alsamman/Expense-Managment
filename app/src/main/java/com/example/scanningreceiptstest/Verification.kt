package com.example.scanningreceiptstest

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chaos.view.PinView
import kotlinx.android.synthetic.main.activity_verification.*


class Verification : AppCompatActivity() {

    private var pinView: PinView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)


        pinView = findViewById(R.id.PinVew);
    }

    fun VerificationOnClick(view: View) {
        val string: String = getString(R.string.true_tick)
        val OTP = pinView!!.text.toString()
        if (OTP == "345678") {
            PinVew.setLineColor(Color.GREEN)
            textView10.setText(string+" OTP Verified")
            textView10.setTextColor(Color.GREEN)
            pinView!!.setTextColor(Color.GREEN)
        } else {
            PinVew.setLineColor(Color.RED)
            textView10.setText("X Incorrect OTP")
            textView10.setTextColor(Color.RED)
            pinView!!.setTextColor(Color.RED)
        }
    }
}