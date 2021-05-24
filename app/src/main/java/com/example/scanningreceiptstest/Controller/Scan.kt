package com.example.scanningreceiptstest.Controller

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.scanningreceiptstest.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText


import java.lang.Exception

class Scan : NavDrawerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        doProcess()

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 101)
        }
    }

    private fun doProcess() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        ActivityCompat.startActivityForResult(this, intent, 101, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val bundle = data?.extras

        val bitmap = bundle?.get("data") as Bitmap?

        if (bitmap == null)
            return

        val visionImage = FirebaseVisionImage.fromBitmap(bitmap)

        val firebaseVision = FirebaseVision.getInstance()

        val recogniz = firebaseVision.onDeviceTextRecognizer

        val task = recogniz.processImage(visionImage)
        task.addOnSuccessListener { firebaseVisionText ->
            val s = firebaseVisionText.text
            val stringSplits = s.split('\n')

            var total = 0.0

            var i = 0
            while (i < stringSplits.size) {

                if (stringSplits[i] == "Total" || stringSplits[i] == "TOTAL" || stringSplits[i] == "TOTAL:" || stringSplits[i] == "Total:")
                    total = findTotal(stringSplits, i)
                i++
            }

            val intent = Intent(this@Scan, AddManually::class.java)
            intent.putExtra("venName", stringSplits[0])
            intent.putExtra("Total", total)
            startActivity(intent)
        }

        task.addOnFailureListener { p0 ->
            Toast.makeText(
                applicationContext,
                "failure ): " + p0.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun findTotal(l1: List<String>, index: Int): Double {
        var t: Double?
        var i = index
        while (i < l1.size) {
            t = l1[i].trim('$').toDoubleOrNull()
            if (t != null) {
                return t
            } else {
                i++
            }
        }
        return 0.0
    }
}

