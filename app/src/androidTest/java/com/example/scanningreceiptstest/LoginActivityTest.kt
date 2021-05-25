package com.example.scanningreceiptstest

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.scanningreceiptstest.Controller.Home
import com.example.scanningreceiptstest.Controller.Login
import com.example.scanningreceiptstest.Model.ExpenseGroup
import com.example.scanningreceiptstest.Model.Person
import com.example.scanningreceiptstest.database.CURRENT_GROUP
import com.example.scanningreceiptstest.database.CURRENT_USER
import com.example.scanningreceiptstest.database.IDatabase
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class LoginActivityTest {

    private lateinit var fakeDatabase: IDatabase

    @Before
    fun setUp() {
        //create fake database instance:
        fakeDatabase = FakeDatabase()

        //add some users to the DB
        val tempDB = fakeDatabase as FakeDatabase
        val group = ExpenseGroup("123", mutableListOf("+16505553434"))
        val user = Person("Sham", "+16505553434", "12345678")
        user.groupId = group.groupID
        tempDB.users.add(user.toDBPerson())
        tempDB.expenseGroups.add(group.toDBExpenseGroup())

        Intents.init()
    }

    @After
    fun cleanUp() {
        Intents.release()
    }

    @Test
    fun phoneNumValidationTest() {
        //launch the activity and change it to use fake database:
        val scenario = ActivityScenario.launch(Login::class.java)
        scenario.onActivity {
            it.database = fakeDatabase
        }

        //empty phone num
        onView(withId(R.id.phone_num_inner)).perform(typeText("015464684"), closeSoftKeyboard())
        onView(withId(R.id.phone_num_inner)).perform(replaceText(""), closeSoftKeyboard())
        onView(withText("Enter Your Phone Number")).check(matches(isDisplayed()))

        //without country code
        onView(withId(R.id.phone_num_inner)).perform(replaceText("0547937554"), closeSoftKeyboard())
        onView(withText("Enter Country code also along with the phone number")).check(
            matches(
                isDisplayed()
            )
        )
    }

    @Test
    fun passwordValidationTest() {
        val scenario = ActivityScenario.launch(Login::class.java)
        scenario.onActivity {
            it.database = fakeDatabase
        }

        //empty password
        onView(withId(R.id.password_inner)).perform(typeText("123456"), closeSoftKeyboard())
        onView(withId(R.id.password_inner)).perform(replaceText(""), closeSoftKeyboard())
        onView(withText("Enter Password")).check(matches(isDisplayed()))

        //invalid password:
        onView(withId(R.id.password_inner)).perform(replaceText("123-casdc-5"), closeSoftKeyboard())
        onView(withText("Password Should Contain Only Characters and Numbers")).check(
            matches(
                isDisplayed()
            )
        )
    }

    @Test
    fun loginTest() {
        val scenario = ActivityScenario.launch(Login::class.java)
        scenario.onActivity {
            it.database = fakeDatabase
        }

        //login with empty phone num and password:
        onView(withId(R.id.Loginbutton)).perform(click())
        assertNull(CURRENT_USER)

        //empty phone num:
        onView(withId(R.id.password_inner)).perform(typeText("123456"), closeSoftKeyboard())
        onView(withId(R.id.Loginbutton)).perform(click())
        assertNull(CURRENT_USER)

        //empty password:
        onView(withId(R.id.phone_num_inner)).perform(replaceText("+966543212452"), closeSoftKeyboard())
        onView(withId(R.id.password_inner)).perform(replaceText(""), closeSoftKeyboard())
        onView(withId(R.id.Loginbutton)).perform(click())
        assertNull(CURRENT_USER)

        //invalid phone num and password:
        onView(withId(R.id.phone_num_inner)).perform(replaceText("0543212452"), closeSoftKeyboard())
        onView(withId(R.id.password_inner)).perform(replaceText("15-m,lkm"), closeSoftKeyboard())
        onView(withId(R.id.Loginbutton)).perform(click())
        assertNull(CURRENT_USER)

        //non-existing phone num:
        onView(withId(R.id.phone_num_inner)).perform(replaceText("+966543212452"), closeSoftKeyboard())
        onView(withId(R.id.password_inner)).perform(replaceText("12345678"), closeSoftKeyboard())
        onView(withId(R.id.Loginbutton)).perform(click())
        assertNull(CURRENT_USER)

        //correct phone num and password:
        onView(withId(R.id.phone_num_inner)).perform(replaceText("+16505553434"), closeSoftKeyboard())
        onView(withId(R.id.password_inner)).perform(replaceText("12345678"), closeSoftKeyboard())
        onView(withId(R.id.Loginbutton)).perform(click())
        assertNotNull(CURRENT_USER)
        assertNotNull(CURRENT_GROUP)
        //check that the home page is opened:
        intended(hasComponent(Home::class.java.name))
    }
}