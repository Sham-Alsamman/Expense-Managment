package com.example.scanningreceiptstest

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.scanningreceiptstest.databinding.ActivityMainBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize binding object:
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        // Set buttons click listeners:
        binding.authButton.setOnClickListener{ startPhoneNumberVerification(binding.phoneNumberEdit.text.toString()) }
        binding.verifyButton.setOnClickListener{ verifyPhoneNumberWithCode(storedVerificationId, binding.verificationCodeEdit.text.toString()) }


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
                    binding.apply {
                        phoneStatusText.text = "Invalid phone number."
                        codeStatusText.text = ""
                    }
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    binding.phoneStatusText.text = "The SMS quota for the project has been exceeded"
                }
                // Show a message and update the UI
                updateUI(STATE_VERIFY_FAILED)
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

                // Update UI
                updateUI(STATE_CODE_SENT)
            }
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        if (!validatePhoneNumber()) {
            return
        }
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        if (TextUtils.isEmpty(code)) {
            binding.apply {
                codeStatusText.text = "Cannot be empty."
                phoneStatusText.text = ""
            }
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
                    updateUI(STATE_SIGNIN_SUCCESS, user)
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        binding.apply {
                            codeStatusText.error = "Invalid code."
                            phoneStatusText.text = ""
                        }
                    }
                    // Update UI
                    updateUI(STATE_SIGNIN_FAILED)
                }
            }
    }

    private fun updateUI(uiState: Int, cred: PhoneAuthCredential) {
        updateUI(uiState, null, cred)
    }

    private fun updateUI(uiState: Int, user: FirebaseUser? = auth.currentUser, cred: PhoneAuthCredential? = null) {
        when (uiState) {
            STATE_CODE_SENT -> {
                // Code sent state, show the verification field, the
                binding.apply {
                    verificationCodeEdit.isEnabled = true
                    verifyButton.isEnabled = true
                    phoneStatusText.text = "code sent"
                    codeStatusText.text = ""
                }
            }
            STATE_VERIFY_FAILED -> {
                // Verification has failed, show all options
                binding.apply {
                    codeStatusText.text = ""
                    phoneStatusText.text = "verification failed"
                }
            }
            STATE_VERIFY_SUCCESS -> {
                // Verification has succeeded, proceed to firebase sign in
                binding.apply {
                    codeStatusText.text = ""
                    phoneStatusText.text = "verification succeeded"
                }
            }
            STATE_SIGNIN_FAILED -> {
                Toast.makeText(this, "sign up failed", Toast.LENGTH_LONG).show()
                binding.apply {
                    phoneStatusText.text = ""
                    codeStatusText.text = ""
                }
            }
            STATE_SIGNIN_SUCCESS -> { /******* hide the codeStatus? ***/
                Toast.makeText(this, "sign up success", Toast.LENGTH_LONG).show()
                binding.apply {
                    phoneStatusText.text = ""
                    codeStatusText.text = ""
                }
            }
        }
    }

    private fun validatePhoneNumber(): Boolean {
        val phoneNumber = binding.phoneNumberEdit.text.toString()
        if (TextUtils.isEmpty(phoneNumber)) {
            binding.apply {
                phoneStatusText.text = "Invalid phone number."
                codeStatusText.text = ""
            }
            return false
        }
        return true
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
