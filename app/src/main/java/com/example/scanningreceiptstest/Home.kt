package com.example.scanningreceiptstest

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.scanningreceiptstest.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Home : AppCompatActivity() {

        var mScanFab: FloatingActionButton? =null
        var mManuallyFab: FloatingActionButton? = null
        var mAddFab: FloatingActionButton? = null


        // These are taken to make visible and invisible along
        // with FABs
        var addManuallyActionText: TextView? = null
        var addScanActionText:   TextView? = null

        // to check whether sub FAB buttons are visible or not.
        var isAllFabsVisible: Boolean? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)

// Register all the FABs with their IDs
            // This FAB button is the Parent
            mScanFab = findViewById(R.id.scan_fab);
            mManuallyFab = findViewById(R.id.manually_fab);
            mAddFab = findViewById(R.id.add_fab);


            addManuallyActionText = findViewById(R.id.addManuallyActionText);
            addScanActionText = findViewById(R.id.addScanActionText);



            mScanFab!!.setVisibility(View.GONE);
            mManuallyFab!!.setVisibility(View.GONE);
            addManuallyActionText!!.setVisibility(View.GONE);
            addScanActionText!!.setVisibility(View.GONE);

            isAllFabsVisible = false;


            mAddFab!!.setOnClickListener {

                if (!isAllFabsVisible!!) {

                    mScanFab!!.show();
                    mManuallyFab!!.show();
                    addManuallyActionText!!.setVisibility(View.VISIBLE);
                    addScanActionText!!.setVisibility(View.VISIBLE);


                    isAllFabsVisible = true;
                } else {

                    mScanFab!!.hide();
                    mManuallyFab!!.hide();
                    addManuallyActionText!!.setVisibility(View.GONE);
                    addScanActionText!!.setVisibility(View.GONE);

                    isAllFabsVisible = false;
                }
            };



            mScanFab!!.setOnClickListener{

                val intent = Intent("android.media.action.IMAGE_CAPTURE")
                startActivity(intent)
            }



            mManuallyFab!!.setOnClickListener{

                val intent = Intent(this,AddManually::class.java)
                startActivity(intent)

            }


        }
    }



