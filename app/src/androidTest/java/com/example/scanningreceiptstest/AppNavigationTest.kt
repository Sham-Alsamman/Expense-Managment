package com.example.scanningreceiptstest

import android.view.Gravity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.scanningreceiptstest.Controller.*
import com.example.scanningreceiptstest.Model.ExpenseGroup
import com.example.scanningreceiptstest.Model.Person
import com.example.scanningreceiptstest.database.CURRENT_GROUP
import com.example.scanningreceiptstest.database.CURRENT_USER
import org.junit.After
import org.junit.Before
import org.junit.Test

class AppNavigationTest {

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun cleanUp() {
        Intents.release()
    }

    @Test
    fun signUp_login_NavigationTest() {
        //start from the welcome page
        var scenario = ActivityScenario.launch(welcomPage2::class.java)

        //open sign up page from welcome page
        onView(withId(R.id.signUPbutton)).perform(click())
        Intents.intended(hasComponent(SignUp::class.java.name))
        scenario.close()

        //open log in page from welcome page
        scenario = ActivityScenario.launch(welcomPage2::class.java)
        onView(withId(R.id.SignINbutton)).perform(click())
        Intents.intended(hasComponent(Login::class.java.name))
        scenario.close()

        Intents.release()
        Intents.init()
        //open sign up page from log in page
        val scenario2 = ActivityScenario.launch(Login::class.java)
        onView(withId(R.id.signUp_login)).perform(click())
        Intents.intended(hasComponent(SignUp::class.java.name))
        scenario.close()

        //open log in page from sign up page
        val scenario3 = ActivityScenario.launch(SignUp::class.java)
        onView(withId(R.id.name_inner)).perform(closeSoftKeyboard())
        onView(withId(R.id.SignInTextView)).perform(click())
        Intents.intended(hasComponent(Login::class.java.name))
    }

    @Test
    fun homeNavigationTest() {
        //initialize current user and group
        CURRENT_USER = Person("Sham", "+16505553434", "123456")
        CURRENT_GROUP = ExpenseGroup("123", mutableListOf(CURRENT_USER!!.phoneNumber))

        var scenario = ActivityScenario.launch(Home::class.java)
        //scenario.close()

        //open add income page:
        onView(withId(R.id.addincome)).perform(click())
        Intents.intended(hasComponent(AddIncome::class.java.name))
        scenario.close()


        //open add expense manually:
        scenario = ActivityScenario.launch(Home::class.java)
        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.manually_fab)).perform(click())
        Intents.intended(hasComponent(AddManually::class.java.name))
        scenario.close()

        //open add expense by scanning:
        scenario = ActivityScenario.launch(Home::class.java)
        onView(withId(R.id.add_fab)).perform(click())
        onView(withId(R.id.scan_fab)).perform(click())
        Intents.intended(hasComponent(Scan::class.java.name))
    }

    @Test
    fun navDrawerTest() {
        //initialize current user and group
        CURRENT_USER = Person("Sham", "+16505553434", "123456")
        CURRENT_GROUP = ExpenseGroup("123", mutableListOf(CURRENT_USER!!.phoneNumber))

        ActivityScenario.launch(Home::class.java)

        //open edit profile page
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
        onView(withId(R.id.navView))
            .perform(NavigationViewActions.navigateTo(R.id.navMenu_editProfile))
        Intents.intended(hasComponent(Profile::class.java.name))

        //open home page
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
        onView(withId(R.id.navView))
            .perform(NavigationViewActions.navigateTo(R.id.navMenu_home))
        Intents.intended(hasComponent(Home::class.java.name))

        //open wallet page
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
        onView(withId(R.id.navView))
            .perform(NavigationViewActions.navigateTo(R.id.navMenu_wallet))
        Intents.intended(hasComponent(Wallet::class.java.name))

        //open report page
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
        onView(withId(R.id.navView))
            .perform(NavigationViewActions.navigateTo(R.id.navMenu_report))
        Intents.intended(hasComponent(Report::class.java.name))

        //open transaction history page
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
        onView(withId(R.id.navView))
            .perform(NavigationViewActions.navigateTo(R.id.navMenu_transactionHistory))
        Intents.intended(hasComponent(TransactionHistory::class.java.name))

        //open invitations page
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
        onView(withId(R.id.navView))
            .perform(NavigationViewActions.navigateTo(R.id.navMenu_invitations))
        Intents.intended(hasComponent(Invitations::class.java.name))

        //open invite partner page
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
        onView(withId(R.id.navView))
            .perform(NavigationViewActions.navigateTo(R.id.navMenu_invitePartner))
        Intents.intended(hasComponent(InvitePartner::class.java.name))

        //open about page
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
        onView(withId(R.id.navView))
            .perform(NavigationViewActions.navigateTo(R.id.navMenu_about))
        Intents.intended(hasComponent(About::class.java.name))

        //logout
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
        onView(withId(R.id.navView))
            .perform(NavigationViewActions.navigateTo(R.id.navMenu_logout))
        Intents.intended(hasComponent(Login::class.java.name))
    }
}