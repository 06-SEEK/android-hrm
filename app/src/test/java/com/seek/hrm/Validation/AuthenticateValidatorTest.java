package com.seek.hrm.Validation;

import junit.framework.TestCase;

import org.junit.Test;

public class AuthenticateValidatorTest extends TestCase {

    @Test
    public void testIsValidEmail() {
        String email = "seek@gmail.com";
        boolean isValid = AuthenticateValidator.isValidEmail(email);
        assertTrue(isValid);
    }

    @Test
    public void testIsInValidEmail() {
        String email = "seek6.com";
        boolean isValid = AuthenticateValidator.isValidEmail(email);
        assertFalse(isValid);
    }
}