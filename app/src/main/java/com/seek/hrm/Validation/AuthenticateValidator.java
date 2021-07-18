package com.seek.hrm.Validation;

import java.util.regex.Pattern;

public class AuthenticateValidator {

    public static boolean isValidEmail(String email){
        final String regex = "^(.+)@(.+)$";
        final Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    };
}
