package com.isw.ussd.whitelable.portal.utils;

import com.isw.ussd.whitelable.portal.vo.APIResponse;
import com.isw.ussd.whitelable.portal.vo.ServiceResponse;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ValidationUtil {

    public static APIResponse<?> generateErrorResponse(BindingResult bindingResult) {
        String errorMessage = bindingResult.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return APIResponse.builder()
                .data(null)
                .code(ServiceResponse.ERROR)
                .description(errorMessage)
                .build();
    }
}
