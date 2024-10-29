package com.isw.ussd.whitelable.portal.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtraData {
    private String actionOn;
    private String customerAccountNumber;
    private String reasonForAction;
    private String details;

    // Might have to create a Country class
    private String country;

    public String getActionOn() {
        return actionOn;
    }

    public void setActionOn(String actionOn) {
        this.actionOn = actionOn;
    }

    public String getCustomerAccountNumber() {
        return customerAccountNumber;
    }

    public void setCustomerAccountNumber(String customerAccountNumber) {
        this.customerAccountNumber = customerAccountNumber;
    }

    public String getReasonForAction() {
        return reasonForAction;
    }

    public void setReasonForAction(String reasonForAction) {
        this.reasonForAction = reasonForAction;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

