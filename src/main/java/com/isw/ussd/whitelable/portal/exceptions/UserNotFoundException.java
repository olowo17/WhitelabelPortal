package com.isw.ussd.whitelable.portal.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userIdIsAlreadyTaken) {
        super(userIdIsAlreadyTaken);
    }
}
