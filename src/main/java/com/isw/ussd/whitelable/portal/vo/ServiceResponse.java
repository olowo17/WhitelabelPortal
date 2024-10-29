package com.isw.ussd.whitelable.portal.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServiceResponse {
    public static final int SUCCESS = 0;
    public static final int ERROR = 10;
    public static final int SESSION_INVALID = 30;
    public static final String GENERAL_ERROR_MESSAGE = "Request Processing Error";
    public static final String GENERAL_SUCCESS_MESSAGE = "Operation Successful";

    private int code;

    private String description;

    public ServiceResponse() {

    }

    public ServiceResponse(int code) {
        this.code = code;
    }

    public ServiceResponse(int code, String description) {
        this(code);
        this.description = description;
    }

}
