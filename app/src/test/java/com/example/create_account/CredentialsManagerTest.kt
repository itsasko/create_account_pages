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

    @Test
    fun testValidateCredentials_thenReturnTrue() {
        val email = "test@te.st"
        val password = "1234"
        val credentialsManager = CredentialsManager()

        val isValid = credentialsManager.login(email, password)
        assertTrue(isValid)
    }

    @Test
    fun testValidateCredentials_thenReturnFalse() {
        val email = "test@te.st"
        val password = "wrongpassword"
        val credentialsManager = CredentialsManager()

        val isValid = credentialsManager.login(email, password)
        assertFalse(isValid)
    }

    @Test
    fun testValidateCredentialsWithDifferentCaps_thenReturnTrue() {
        val email = "TEst@TE.st"
        val password = "1234"
        val credentialsManager = CredentialsManager()

        val isValid = credentialsManager.login(email, password)
        assertTrue(isValid)
    }

    @Test
    fun givenProperUnusedCredentials_whenUserRegister_thenSucceed() {
        val credentialsManager = CredentialsManager()
        val newEmail = "another@te.st"
        val newPassword = "1234qwer"

        val isValid = credentialsManager.register("Full name", newEmail, "123456", newPassword)
        assertTrue(isValid)
    }

    @Test
    fun givenProperUsedCredentials_whenUserRegister_thenFailed() {
        val credentialsManager = CredentialsManager()
        val newEmail = "TEst@TE.st"
        val newPassword = "12345"

        val isValid = credentialsManager.register("Full name", newEmail, "123456", newPassword)
        assertFalse(isValid)
    }
}


