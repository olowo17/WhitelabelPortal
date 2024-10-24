package UssdWhitelabelPortal.whitelabel.enums;

import lombok.Getter;

@Getter
public enum PendingRequestStatus {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    DECLINED("DECLINED");

    private final String status;

    PendingRequestStatus(String status) {
        this.status= status;
    }

}
