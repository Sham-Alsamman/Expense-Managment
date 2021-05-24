package com.example.scanningreceiptstest

import com.example.scanningreceiptstest.Model.Person
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PersonTest {

    private lateinit var personTest: Person

    @Before
    fun setup() {
        personTest = Person("Aya Jaradat", "+16505553434", "12345678")
    }

    @Test
    fun monthlySalaryTest1() {
        personTest.monthlySalary = 250.0

        val result = personTest.monthlySalary
        assertEquals(250.0, result, .001)
    }

    @Test
    fun monthlySalaryTest2() {
        personTest.monthlySalary = -300.0

        val result = personTest.monthlySalary
        assertEquals(0.0, result, .001)
    }

    @Test
    fun remainingTest1() {
        personTest.remaining = 100.0

        val result = personTest.remaining
        assertEquals(100.0, result, .001)
    }

    @Test
    fun remainingTest2() {
        personTest.remaining = -300.0

        val result = personTest.remaining
        assertEquals(0.0, result, .001)
    }

    @Test
    fun totalIncomeTest1() {
        personTest.totalIncome = 500.0

        val result = personTest.totalIncome
        assertEquals(500.0, result, .001)
    }

    @Test
    fun totalIncomeTest2() {
        personTest.totalIncome = -500.0

        val result = personTest.totalIncome
        assertEquals(0.0, result, .001)
    }

    @Test
    fun savingAmountTest1() {
        personTest.savingAmount = 120.0

        val result = personTest.savingAmount
        assertEquals(120.0, result, .001)
    }

    @Test
    fun savingAmountTest2() {
        personTest.savingAmount = -100.0

        val result = personTest.savingAmount
        assertEquals(0.0, result, .001)
    }

    @Test
    fun savingWalletTest1() {
        personTest.savingWallet = 500.0

        val result = personTest.savingWallet
        assertEquals(500.0, result, .001)
    }

    @Test
    fun savingWalletTest2() {
        personTest.savingWallet = -300.0

        val result = personTest.savingWallet
        assertEquals(0.0, result, .001)
    }

    @Test
    fun changeGroupTest() {
        personTest.changeGroup("123")
        assertEquals("123", personTest.groupId)
    }

    @Test
    fun addExpenseIfPossibleTest1() {
        val expense = 10.0
        personTest.remaining = 100.0

        val result = personTest.addExpenseIfPossible(expense)
        assertEquals(90.0, personTest.remaining, .001)
        assertTrue(result)
    }

    @Test
    fun addExpenseIfPossibleTes2() {
        val expense = 120.0
        personTest.remaining = 100.0

        val result =personTest.addExpenseIfPossible(expense)

        assertEquals(100.0, personTest.remaining, .001)
        assertFalse(result)
    }

    @Test
    fun canWithdrawFromSavingsTest1() {
        val expense = 150.0
        personTest.remaining = 100.0
        personTest.savingWallet = 100.0

        val result = personTest.canWithdrawFromSavings(expense)

        assertTrue(result)
    }

    @Test
    fun canWithdrawFromSavingsTest2() {
        val expense = 150.0
        personTest.remaining = 50.0
        personTest.savingWallet = 20.0

        val result = personTest.canWithdrawFromSavings(expense)

        assertFalse(result)
    }

    @Test
    fun canWithdrawFromSavingsTest3() {
        val expense = -150.0
        personTest.remaining = 50.0
        personTest.savingWallet = 20.0

        val result = personTest.canWithdrawFromSavings(expense)

        assertFalse(result)
    }

    @Test
    fun withdrawFromSavingTest() {
        val expense = 150.0
        personTest.remaining = 50.0
        personTest.savingWallet = 200.0

        personTest.withdrawFromSaving(expense)

        assertEquals(100.0, personTest.savingWallet, .001)
        assertEquals(0.0, personTest.remaining, .001)
    }

    @Test
    fun addIncomeTest1() {
        val income = 150.0
        personTest.remaining = 150.0
        personTest.totalIncome = 500.0

        personTest.addIncome(income)

        assertEquals(300.0, personTest.remaining, .001)
        assertEquals(650.0, personTest.totalIncome, .001)
    }

    @Test
    fun addIncomeTest2() {
        val income = -500.0
        personTest.remaining = 150.0
        personTest.totalIncome = 500.0

        personTest.addIncome(income)
        assertEquals(150.0, personTest.remaining, .001)
        assertEquals(500.0, personTest.totalIncome, .001)
    }

    @Test
    fun atEndOfMonthTest() {
        personTest.remaining = 100.0
        personTest.savingWallet = 200.0

        personTest.atEndOfMonth()

        assertEquals(300.0, personTest.savingWallet, .001)
        assertEquals(0.0, personTest.remaining, .001)
        assertEquals(0.0, personTest.totalIncome, .001)
    }

    @Test
    fun addSalaryAndCalculateSavingTest() {
        personTest.monthlySalary = 800.0
        personTest.totalIncome = 100.0
        personTest.savingAmount = 15.0
        personTest.remaining = 100.0
        personTest.savingWallet = 200.0

        personTest.addSalaryAndCalculateSaving()

        assertEquals(420.0, personTest.savingWallet, .001)
        assertEquals(680.0, personTest.remaining, .001)
        assertEquals(800.0, personTest.totalIncome, .001)
    }

    @Test
    fun toDBPersonTest() {
        val result = personTest.toDBPerson()

        assertEquals(result.phoneNumber, result.phoneNumber)
        assertEquals(result.name, result.name)
        assertEquals(result.password, result.password)
        assertEquals(result.groupId, result.groupId)
        assertEquals(result.monthlySalary, result.monthlySalary, .001)
        assertEquals(result.totalIncome, result.totalIncome, .001)
        assertEquals(result.savingAmount, result.savingAmount, .001)
        assertEquals(result.savingWallet, result.savingWallet, .001)
        assertEquals(result.remaining, result.remaining, .001)
    }

}