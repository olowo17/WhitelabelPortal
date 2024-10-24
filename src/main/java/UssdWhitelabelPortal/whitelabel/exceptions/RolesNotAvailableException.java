package UssdWhitelabelPortal.whitelabel.exceptions;

public class RolesNotAvailableException extends RuntimeException {
    public RolesNotAvailableException(String userIdIsAlreadyTaken) {
        super(userIdIsAlreadyTaken);
    }
}
