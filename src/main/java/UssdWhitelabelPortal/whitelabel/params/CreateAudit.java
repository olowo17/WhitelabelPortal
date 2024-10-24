package UssdWhitelabelPortal.whitelabel.params;


import UssdWhitelabelPortal.whitelabel.enums.AuditType;
import UssdWhitelabelPortal.whitelabel.enums.PendingRequestStatus;
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

    private Long institutionId;

    private Long auditorId;

    private PendingRequestStatus status;

    private String customerAccountNumber;

    private String reasonForAction;

}

