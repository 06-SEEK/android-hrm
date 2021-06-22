package com.seek.hrm.Session;

import com.seek.hrm.Parsing.LoginResponse;

public class LoginSession {

    private static LoginResponse login = null;

    public LoginSession() {
    }

    public static LoginResponse getInstance(){
        return login;
    }

    public static void setInstance(LoginResponse login){
        LoginSession.login = login;
    }

    public static void clearInstance(){
        LoginSession.login = null;
    }
}
