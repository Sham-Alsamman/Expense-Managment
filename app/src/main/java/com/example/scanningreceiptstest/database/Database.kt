package com.example.scanningreceiptstest.database

import com.example.scanningreceiptstest.database.Database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object Database {

    val database = Firebase.database
}