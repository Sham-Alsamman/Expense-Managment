package com.example.scanningreceiptstest.Controller

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.chaos.view.PinView
import com.example.scanningreceiptstest.Model.Person
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.SaveSharedPreference
import com.example.scanningreceiptstest.database.*
import com.example.scanningreceiptstest.setSalaryAlarm
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_verification.*
import java.util.concurrent.TimeUnit


const val PHONE_NUMBER_EXTRA = "phoneNum"

class Verification() : NavDrawerActivity () {

    private var userPhoneNum: String = ""

    private lateinit var auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private var person2 : Person? = null
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    private var pinView: PinView? = null

    private var countDown: CountDownTimer = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            resendCodeTv.isEnabled = false
            resendCodeTv.text = String.format("Re-Send Code in 00:%02d", millisUntilFinished / 1000)

        }

        override fun onFinish() {
            resendCodeTv.text = "Re-Send Code"
            resendCodeTv.isEnabled = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        person2 = intent?.extras?.get("Person") as Person?
        textView10.text="Enter a 6 digit number that will be sent to " + person2!!.phoneNumber ;
        textView10.gravity=Gravity.CENTER

        pinView = findViewById(R.id.PinVew)
        verify_button.setOnClickListener{
            verifyPhoneNumberWithCode(storedVerificationId, pinView!!.text.toString())
        }
        resendCodeTv.setOnClickListener{
            startPhoneNumberVerification(userPhoneNum)
        }

        //get phone num from sign up page:
        val phone = intent?.extras?.getString(PHONE_NUMBER_EXTRA)

        if(phone != null) userPhoneNum = phone

        // Initialize Firebase auth
        auth = Firebase.auth

        // Initialize phone auth callbacks
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.

                // Update the UI and attempt sign in with the phone credential
                updateUI(STATE_VERIFY_SUCCESS, credential)
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(applicationContext, "Invalid Phone Number!", Toast.LENGTH_SHORT).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(
                        applicationContext,
                        "The SMS quota for the project has been exceeded",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // Show a message and update the UI
                updateUI(STATE_VERIFY_FAILED)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

                //start the count down
                countDown.start()

                // Update UI
                updateUI(STATE_CODE_SENT)
            }
        }

        startPhoneNumberVerification(userPhoneNum)
    }


    override fun onDestroy() {
        countDown.cancel()

        Log.i("onDestroy", "called")
        super.onDestroy()
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        if(phoneNumber.isNotEmpty()) {
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }
        else
            Toast.makeText(this, "Phone number is empty!", Toast.LENGTH_SHORT).show()

        Log.i("start", "startPhoneNumberVerification")
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(
                applicationContext,
                "Please enter the verification code",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = task.result?.user
                    //make pin view green
                    val tick: String = getString(R.string.true_tick)
                    PinVew.setLineColor(Color.GREEN)
                    textView10.text = "$tick Code verified"
                    textView10.setTextColor(Color.GREEN)
                    pinView!!.setTextColor(Color.GREEN)
                    countDown.cancel()
                    updateUI(STATE_SIGNIN_SUCCESS, user)
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        // make the pin view red
                        PinVew.setLineColor(Color.RED)
                        textView10.text = "X Incorrect code"
                        textView10.setTextColor(Color.RED)
                        pinView!!.setTextColor(Color.RED)
                    }
                    // Update UI
                    updateUI(STATE_SIGNIN_FAILED)
                }
            }
    }


    private fun updateUI(uiState: Int, cred: PhoneAuthCredential) {
        updateUI(uiState, null, cred)
    }

    private fun updateUI(
        uiState: Int,
        user: FirebaseUser? = auth.currentUser,
        cred: PhoneAuthCredential? = null
    ) {
        when (uiState) {
            STATE_CODE_SENT -> {
                // Code sent state, show the verification field, the
                Toast.makeText(applicationContext, "Code sent", Toast.LENGTH_SHORT).show()
            }
            STATE_VERIFY_FAILED -> {
                // Verification has failed, show all options
                Toast.makeText(
                    applicationContext,
                    "verification failed, check your network and your phone number correctness",
                    Toast.LENGTH_LONG
                ).show()
            }
            STATE_VERIFY_SUCCESS -> {
                // Verification has succeeded, proceed to firebase sign in

            }
            STATE_SIGNIN_FAILED -> {
                Toast.makeText(this, "sign up failed", Toast.LENGTH_SHORT).show()
            }
            STATE_SIGNIN_SUCCESS -> {
                // get the person object from sign up page
                if (person2 != null) {

                    val dbExpe = DBExpenseGroup()
                    dbExpe.partners.add(person2!!.phoneNumber)
                    val ExpenseGroup = database.addNewExpenseGroup(dbExpe).toExpenseGroup()
                    val newPerson = Person(
                        person2!!.phoneNumber,
                        person2!!.name,
                        person2!!.password,
                        ExpenseGroup.groupID,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        0.0
                    )
                    CURRENT_USER = newPerson
                    database.addNewUser(newPerson.toDBPerson(), ::onUserAdded)

                    setSalaryAlarm(this)
                }
            }
        }
    }

    private fun onUserAdded(successful: Boolean) {
        if (successful){
            database.getExpenseGroup(CURRENT_USER!!.groupId, ::DbResultExpenseGroup)
        }
    }

    private fun DbResultExpenseGroup(ExpenseGroup: DBExpenseGroup) {
        CURRENT_GROUP = ExpenseGroup.toExpenseGroup()
        if(CURRENT_GROUP!=null) {
            Toast.makeText(this, "Sign up success", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Wallet::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            SaveSharedPreference.saveUserData(this)
            finish()
        }
    }


    companion object {
        private const val TAG = "PhoneAuthActivity"
        private const val STATE_CODE_SENT = 2
        private const val STATE_VERIFY_FAILED = 3
        private const val STATE_VERIFY_SUCCESS = 4
        private const val STATE_SIGNIN_FAILED = 5
        private const val STATE_SIGNIN_SUCCESS = 6
    }
}