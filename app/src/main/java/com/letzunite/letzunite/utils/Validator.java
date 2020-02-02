package com.letzunite.letzunite.utils;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Deven Singh on 5/18/2018.
 */
@Singleton
public class Validator {

    @Inject
    public Validator() {
    }

    public Boolean isValidEmailAddress(String emailId) {
        return emailId != null && emailId.length() > 3 && emailId.contains("@");
    }

    public Boolean isValidNumber(String mobileNum) {
        String regEx = "^[6-9]\\d{9}$";
        return mobileNum.matches(regEx);
    }

    public Boolean isValidPassword(String password) {
        String regEx = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,16}$";
        return password.matches(regEx);
    }

    public Boolean isEmptyString(String text) {
        return text != null && text.trim().length() > 0;
    }

    public Boolean isNullObject(Object obj) {
        return obj == null;
    }
}
