package com.saha.lifeplustenicaltest.utils.helpers

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HashHelperTest {

    @Test
    fun validate_password_expected_true() {
        val password = "Hello_world"
        val hashText = HashHelper.getHashPassword(password)
        val isValid = HashHelper.verifyPassword(password, hashText)
        assertTrue(isValid)
    }

    @Test
    fun validate_password_input2_expected_true() {
        val password = "As123456"
        val hashText = HashHelper.getHashPassword(password)
        val isValid = HashHelper.verifyPassword(password, hashText)
        assertTrue(isValid)
    }

    @Test
    fun validate_password_input3_expected_true() {
        val password = ""

        val hashText = HashHelper.getHashPassword(password)

        val isValid = HashHelper.verifyPassword(password, hashText)

        assertTrue(isValid)
    }

    @Test
    fun validate_password_expected_false() {
        val password = "As123456"
        val hashTest = HashHelper.getHashPassword(password)
        val isValid = HashHelper.verifyPassword("as123456", hashTest)
        assertFalse(isValid)
    }
}