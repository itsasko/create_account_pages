package com.example.create_account
import junit.framework.TestCase.assertFalse


import org.junit.Test
import org.junit.Assert.*

class CredentialsManagerTest {

    @Test
    fun givenEmptyEmail_thenReturnFalse() {
        val credentialsManager = CredentialsManager()
        val email = ""

        val isEmailValid = credentialsManager.isEmailValid(email)
        assertFalse(isEmailValid)
    }

    @Test
    fun givenProperEmail_thenReturnTrue() {
        val credentialsManager = CredentialsManager()
        val email = "email@te.st"

        val isEmailValid = credentialsManager.isEmailValid(email)
        assertTrue(isEmailValid)
    }

    @Test
    fun givenWrongEmailFormat_thenReturnFalse() {
        val credentialsManager = CredentialsManager()
        val email = "уmail@domain..com"

        val isEmailValid = credentialsManager.isEmailValid(email)
        assertFalse(isEmailValid)
    }

    @Test
    fun givenEmptyPassword_thenReturnFalse() {
        val credentialsManager = CredentialsManager()
        val password = ""

        val isPasswordValid = credentialsManager.isPasswordValid(password)
        assertFalse(isPasswordValid)
    }

    @Test
    fun givenFilledPassword_thenReturnTrue() {
        val credentialsManager = CredentialsManager()
        val password = "somePassword1234"

        val isPasswordValid = credentialsManager.isPasswordValid(password)
        assertTrue(isPasswordValid)
    }
}


