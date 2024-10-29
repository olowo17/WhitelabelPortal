package com.isw.ussd.whitelable.portal.params;


import com.isw.ussd.whitelable.portal.enums.AuditType;
import com.isw.ussd.whitelable.portal.enums.PendingRequestStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAudit implements Serializable {

    private final Date createDate = new Date();


    private AuditType auditType;

    private String details;

    private String userIp;

    private String actionOn;

    private String actionBy;

    @NotNull(message = "Institution ID cannot be null or empty")
    private Long institutionId;

    private Long auditorId;

    private PendingRequestStatus status;

    @NotNull(message = "Customer account cannot be null or empty")
    @Pattern(regexp = "^\\d{8,12}$", message = "Invalid account number format")
    private String customerAccountNumber;

    @NotNull(message = "Reason for action cannot be null or empty")
    private String reasonForAction;

}

