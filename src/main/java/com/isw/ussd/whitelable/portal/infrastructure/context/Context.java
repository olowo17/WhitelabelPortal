package com.isw.ussd.whitelable.portal.infrastructure.context;

import com.isw.ussd.whitelable.portal.utils.GeneratorHelper;
import com.isw.ussd.whitelable.portal.utils.LocaleHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Context {

    private final long initializeTime = System.currentTimeMillis();
    private String traceID = GeneratorHelper.generateTraceContextId("CTX");
    private String sourceIpAddress;
    private String eventPath;
    private String service;
    private String contextMessage;
    private String languageCode = "EN";

    public String getMessage(String messageKey) {
        return LocaleHandler.getMessage(getLanguageCode(), messageKey);
    }

}
