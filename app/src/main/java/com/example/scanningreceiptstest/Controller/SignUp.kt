package com.example.scanningreceiptstest.Controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.scanningreceiptstest.Model.Person
import com.example.scanningreceiptstest.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignUp : NavDrawerActivity() {

    private var phoneNumExist = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val reg = "\\p{Punct}"
        val Name = findViewById<TextInputLayout>(R.id.NameET)
        val PhoneSignUp = findViewById<TextInputLayout>(R.id.PhoneETSignUp)
        val pass = findViewById<TextInputLayout>(R.id.PasswordETSignUp)
        val RePass = findViewById<TextInputLayout>(R.id.RepasswordET)

        Name.editText?.doOnTextChanged { text, start, before, count ->
            val pattern = Pattern.compile(reg)
            val matcher: Matcher = pattern.matcher(text!!)
            if (matcher.find()) {
                Name.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                Name.error = "Name Should Contain Only Characters and Numbers"
            } else if (text.isNullOrEmpty()) {
                Name.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                Name.error = "Enter Your Name"
            } else {
                Name.setEndIconDrawable(0)
                Name.error = null
            }
        }
        PhoneSignUp.editText?.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) {
                PhoneSignUp.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                PhoneSignUp.error = "Enter Your Phone Number"
            } else if (!text.startsWith("+")) {
                PhoneSignUp.setEndIconDrawable(R.drawable.ic_baseline_error_24)
                PhoneSignUp.error = "Enter Country code also along with the phone number"
            }
            // to check if the phone number already register in firebase
            else {
                PhoneSignUp.setEndIconDrawable(0)
                checkIfPartnerExist(text.toString())
            }
        }
        pass.editText?.doOnTextChanged { text, start, before, count ->
            val pattern = Pattern.compile(reg)
            val matcher: Matcher = pattern.matcher(text!!)
            if (matcher.find()) {
                pass.error = "Password should contain only characters and numbers"
            } else if (text.isNullOrEmpty()) {
                pass.error = "Enter Password"
            } else if (text.length < 8) {
                pass.error = "Password must at least 8 characters"
            } else {
                PasswordETSignUp.setEndIconActivated(true)
                pass.error = null
            }
        }
        RePass.editText?.doOnTextChanged { text, start, before, count ->
            val match: String = pass.editText?.text.toString()
            val match2: String = RePass.editText?.text.toString()
            if (match2 != match) {
                RePass.error = "Password are not matching"
            } else if (text.isNullOrEmpty()) {
                RePass.error = "Re-type Password"
            } else {
                RePass.error = null
            }
        }
    }


    fun SignInTextView(view: View) {
        val i = Intent(applicationContext, Login::class.java)
        startActivity(i)
    }

    fun goToVerification(view: View) {
        /***check if all fields are correct..**/
        if (NameET.editText?.text!!.isEmpty() && PhoneETSignUp.editText?.text!!.isEmpty() && PasswordETSignUp.editText?.text!!.isEmpty() && RepasswordET.editText?.text!!.isEmpty()) {
            Toast.makeText(applicationContext, "Please fill all fields ", Toast.LENGTH_LONG).show()
        } else if (NameET.editText?.text!!.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter name ", Toast.LENGTH_LONG).show()
        } else if (PhoneETSignUp.editText?.text!!.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter phone number ", Toast.LENGTH_LONG)
                .show()
        } else if (PasswordETSignUp.editText?.text!!.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter password ", Toast.LENGTH_LONG).show()
        } else if (RepasswordET.editText?.text!!.isEmpty()) {
            Toast.makeText(applicationContext, "Please Re-type password ", Toast.LENGTH_LONG).show()
        } else if (PhoneETSignUp.error != null) {
            Toast.makeText(applicationContext, PhoneETSignUp.error, Toast.LENGTH_LONG).show()
        } else if (NameET.error != null) {
            Toast.makeText(applicationContext, NameET.error, Toast.LENGTH_LONG).show()

        } else if (PasswordETSignUp.error != null) {
            Toast.makeText(applicationContext, PasswordETSignUp.error, Toast.LENGTH_LONG).show()

        } else if (RepasswordET.error != null) {
            Toast.makeText(applicationContext, RepasswordET.error, Toast.LENGTH_LONG).show()

        } else {
            //open verification page:

            val username = NameET.editText?.text!!.toString()
            val phoneNUMBER = PhoneETSignUp.editText?.text!!.toString()
            val password = PasswordETSignUp.editText?.text!!.toString()
            val hashPass = BCrypt.withDefaults().hashToString(12, password.toCharArray())

            // error when add person
            val person = Person(
                username,
                phoneNUMBER,
                hashPass
            )

            val intent = Intent(applicationContext, Verification::class.java)
            intent.putExtra(PHONE_NUMBER_EXTRA, PhoneETSignUp.editText?.text.toString())
            intent.putExtra("Person", person)
            startActivity(intent)
        }

    }

    private fun checkIfPartnerExist(phoneNum: String) {
        //check if the phone number exists in the database
        database.checkIfUserExist(phoneNum, ::onDBResult)
    }

    private fun onDBResult(exist: Boolean) {
        if (exist) {
            phoneNumExist = true
            PhoneETSignUp.setEndIconDrawable(R.drawable.ic_baseline_error_24)
            PhoneETSignUp.error = "This phone number already registered in the app"
        } else {
            phoneNumExist = false
            PasswordETSignUp.setEndIconDrawable(0)
            PhoneETSignUp.error = null
        }
    }


}

