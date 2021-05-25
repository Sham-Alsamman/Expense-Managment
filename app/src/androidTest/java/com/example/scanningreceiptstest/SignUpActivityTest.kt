package com.example.scanningreceiptstest

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.scanningreceiptstest.Controller.Home
import com.example.scanningreceiptstest.Controller.Login
import com.example.scanningreceiptstest.Controller.SignUp
import com.example.scanningreceiptstest.Controller.Verification
import com.example.scanningreceiptstest.database.IDatabase
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test

class SignUpActivityTest {
    private lateinit var fakeDatabase: IDatabase

    @Before
    fun setUp() {
        //create fake database instance:
        fakeDatabase = FakeDatabase()

        Intents.init()
    }

    @After
    fun cleanUp() {
        Intents.release()
    }

    @Test
    fun nameValidationTest() {
        //launch the activity and change it to use fake database:
        val scenario = ActivityScenario.launch(SignUp::class.java)
        scenario.onActivity {
            it.database = fakeDatabase
        }

        //empty name
        onView(withId(R.id.name_inner)).perform(typeText("sss"), closeSoftKeyboard())
        onView(withId(R.id.name_inner)).perform(replaceText(""), closeSoftKeyboard())
        onView(withText("Enter Your Name")).check(matches(isDisplayed()))

        //invalid name
        onView(withId(R.id.name_inner)).perform(replaceText("sham**1"), closeSoftKeyboard())
        onView(withText("Name Should Contain Only Characters and Numbers")).check(
            matches(
                isDisplayed()
            )
        )
    }

    @Test
    fun phoneNumValidationTest() {
        //launch the activity and change it to use fake database:
        val scenario = ActivityScenario.launch(SignUp::class.java)
        scenario.onActivity {
            it.database = fakeDatabase
        }

        //empty phone num
        onView(withId(R.id.phone_inner)).perform(typeText("015464684"), closeSoftKeyboard())
        onView(withId(R.id.phone_inner)).perform(replaceText(""), closeSoftKeyboard())
        onView(withText("Enter Your Phone Number")).check(matches(isDisplayed()))

        //without country code
        onView(withId(R.id.phone_inner)).perform(replaceText("0547937554"), closeSoftKeyboard())
        onView(withText("Enter Country code also along with the phone number")).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun passwordValidationTest() {
        val scenario = ActivityScenario.launch(SignUp::class.java)
        scenario.onActivity {
            it.database = fakeDatabase
        }

        //empty password
        onView(withId(R.id.pass_inner)).perform(typeText("123456"), closeSoftKeyboard())
        onView(withId(R.id.pass_inner)).perform(replaceText(""), closeSoftKeyboard())
        onView(withText("Enter Password")).check(matches(isDisplayed()))

        //invalid password:
        onView(withId(R.id.pass_inner)).perform(replaceText("123-casdc-5"), closeSoftKeyboard())
        onView(withText("Password should contain only characters and numbers")).check(
            matches(
                isDisplayed()
            )
        )

        //short password:
        onView(withId(R.id.pass_inner)).perform(replaceText("123"), closeSoftKeyboard())
        onView(withText("Password must at least 8 characters")).check(matches(isDisplayed()))
    }

    @Test
    fun retypePasswordValidationTest() {
        val scenario = ActivityScenario.launch(SignUp::class.java)
        scenario.onActivity {
            it.database = fakeDatabase
        }

        //empty password
        onView(withId(R.id.retype_inner)).perform(typeText("123456"), closeSoftKeyboard())
        onView(withId(R.id.retype_inner)).perform(replaceText(""), closeSoftKeyboard())
        onView(withText("Re-type Password")).check(matches(isDisplayed()))

        //invalid password:
        onView(withId(R.id.pass_inner)).perform(typeText("12345678"), closeSoftKeyboard())
        onView(withId(R.id.retype_inner)).perform(replaceText("87654321"), closeSoftKeyboard())
        onView(withText("Password are not matching")).check(matches(isDisplayed()))
    }

    @Test
    fun signUpTest() {
        val scenario = ActivityScenario.launch(SignUp::class.java)
        scenario.onActivity {
            it.database = fakeDatabase
        }

        //all fields are empty:
        onView(withId(R.id.loginbutton)).perform(click())
        Intents.intended(not(hasComponent(Verification::class.java.name)))

        //invalid input:
        onView(withId(R.id.name_inner)).perform(typeText("sham"), closeSoftKeyboard())
        onView(withId(R.id.phone_inner)).perform(typeText("015464684"), closeSoftKeyboard())
        onView(withId(R.id.pass_inner)).perform(typeText("123456"), closeSoftKeyboard())
        onView(withId(R.id.retype_inner)).perform(replaceText("87654321"), closeSoftKeyboard())
        onView(withId(R.id.loginbutton)).perform(click())
        Intents.intended(not(hasComponent(Verification::class.java.name)))

        //valid input:
        onView(withId(R.id.name_inner)).perform(replaceText("sham"), closeSoftKeyboard())
        onView(withId(R.id.phone_inner)).perform(replaceText("+15464684244"), closeSoftKeyboard())
        onView(withId(R.id.pass_inner)).perform(replaceText("12345678"), closeSoftKeyboard())
        onView(withId(R.id.retype_inner)).perform(replaceText("12345678"), closeSoftKeyboard())
        //onView(withId(R.id.loginbutton)).perform(click())
        //Intents.intended(hasComponent(Verification::class.java.name))
    }
}