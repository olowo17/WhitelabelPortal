package com.isw.ussd.whitelable.portal.services;

import com.isw.ussd.whitelable.portal.utils.LocaleHandler;
import com.isw.ussd.whitelable.portal.utils.RabbitMQ;
import com.isw.ussd.whitelable.portal.vo.AdditionalPortalParameters;
import com.isw.ussd.whitelable.portal.vo.ResponseMessages;
import com.isw.ussd.whitelable.portal.vo.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MessagingService {

    private static final Logger logger = Logger.getLogger(MessagingService.class.getName());

    @Autowired
    private RabbitMQ messaging;

    public ServiceResponse sendSMS(String message, String phone, AdditionalPortalParameters params) {
        try {
            messaging.sendSMS(phone, message, params.getUser().getInstitution().getCode(), "", "");
            return new ServiceResponse(ServiceResponse.SUCCESS, LocaleHandler.getMessage(params.getLanguageCode(), ResponseMessages.GENERAL_SUCCESS_MESSAGE));
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            return new ServiceResponse(ServiceResponse.ERROR, LocaleHandler.getMessage(params.getLanguageCode(), ResponseMessages.GENERAL_ERROR_MESSAGE));
        }
    }

    public ServiceResponse sendEmail(String message, String recipient, String subject, String institutionCode, String languageCode) {
        try {
            messaging.sendEmail(recipient, subject, message, institutionCode);
            return new ServiceResponse(ServiceResponse.SUCCESS, LocaleHandler.getMessage(languageCode, ResponseMessages.GENERAL_SUCCESS_MESSAGE));
        } catch (Exception ex) {
            logger.log(Level.SEVERE, String.format("Send Email Exception: %s", ex.getMessage()));
            return new ServiceResponse(ServiceResponse.ERROR, LocaleHandler.getMessage(languageCode, ResponseMessages.GENERAL_ERROR_MESSAGE));
        }
    }
}
