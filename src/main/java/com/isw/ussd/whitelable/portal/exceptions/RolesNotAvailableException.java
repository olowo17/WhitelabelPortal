package com.isw.ussd.whitelable.portal.exceptions;

public class RolesNotAvailableException extends RuntimeException {
    public RolesNotAvailableException(String userIdIsAlreadyTaken) {
        super(userIdIsAlreadyTaken);
    }
}
