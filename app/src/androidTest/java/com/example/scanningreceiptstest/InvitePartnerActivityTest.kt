package com.example.scanningreceiptstest

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.scanningreceiptstest.Controller.InvitePartner
import com.example.scanningreceiptstest.Model.ExpenseGroup
import com.example.scanningreceiptstest.Model.Person
import com.example.scanningreceiptstest.database.CURRENT_GROUP
import com.example.scanningreceiptstest.database.CURRENT_USER
import com.example.scanningreceiptstest.database.IDatabase
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class InvitePartnerActivityTest {
    private lateinit var fakeDatabase: IDatabase
    private lateinit var otherUser: Person

    @Before
    fun setUp() {
        //create fake database instance:
        fakeDatabase = FakeDatabase()

        //initialize current user and group
        CURRENT_USER = Person("Sham", "+16505553434", "123456")
        CURRENT_GROUP = ExpenseGroup("123", mutableListOf(CURRENT_USER!!.phoneNumber))

        //add some users to the DB
        val tempDB = fakeDatabase as FakeDatabase
        otherUser = Person("Aya", "+123456789", "987654")
        tempDB.users.add(CURRENT_USER!!.toDBPerson())
        tempDB.users.add(otherUser.toDBPerson())
    }

    @Test
    fun invite_emptyPhoneNum() {
        //launch the activity and change it to use fake database:
        val scenario = ActivityScenario.launch(InvitePartner::class.java)
        scenario.onActivity {
            it.database = fakeDatabase
        }

        //enter empty phone num then click send, an error msg should appear
        onView(withId(R.id.invitePhoneNumInnerET)).perform(typeText(""), closeSoftKeyboard())
        onView(withId(R.id.sendBtn)).perform(click())
        onView(withText("Enter your partner's phone number")).check(matches(isDisplayed()))

        //check that no invitation is added
        assertFalse((fakeDatabase as FakeDatabase).checkIfInvitationExists("", CURRENT_USER!!.name))
    }

    @Test
    fun invite_invalidPhoneNum() {
        //launch the activity and change it to use fake database:
        val scenario = ActivityScenario.launch(InvitePartner::class.java)
        scenario.onActivity {
            it.database = fakeDatabase
        }

        val invalidNum = "07912345678"
        onView(withId(R.id.invitePhoneNumInnerET)).perform(typeText("07912345678"), closeSoftKeyboard())
        onView(withId(R.id.sendBtn)).perform(click())
        onView(withText("Enter a complete phone number with country code (ex. +999795555555)")).check(
            matches(isDisplayed())
        )

        assertFalse((fakeDatabase as FakeDatabase).checkIfInvitationExists(invalidNum, CURRENT_USER!!.name))
    }

    @Test
    fun invite_nonExistingPhoneNum() {
        //launch the activity and change it to use fake database:
        val scenario = ActivityScenario.launch(InvitePartner::class.java)
        scenario.onActivity {
            it.database = fakeDatabase
        }

        val nonExistingNum = "+7912345678"
        onView(withId(R.id.invitePhoneNumInnerET)).perform(typeText(nonExistingNum), closeSoftKeyboard())
        onView(withId(R.id.sendBtn)).perform(click())
        onView(withText("This phone number is not registered in the app")).check(
            matches(isDisplayed())
        )

        assertFalse((fakeDatabase as FakeDatabase).checkIfInvitationExists(nonExistingNum, CURRENT_USER!!.name))
    }

    @Test
    fun invite_validPhoneNum() {
        //launch the activity and change it to use fake database:
        val scenario = ActivityScenario.launch(InvitePartner::class.java)
        scenario.onActivity {
            it.database = fakeDatabase
        }

        onView(withId(R.id.invitePhoneNumInnerET)).perform(typeText(otherUser.phoneNumber), closeSoftKeyboard())
        onView(withId(R.id.sendBtn)).perform(click())

        assertTrue((fakeDatabase as FakeDatabase).checkIfInvitationExists(otherUser.phoneNumber, CURRENT_USER!!.name))
    }
}