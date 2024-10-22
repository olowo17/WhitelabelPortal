package lazyprogrammer.jwtdemo.exceptions;

public class RolesNotAvailableException extends RuntimeException {
    public RolesNotAvailableException(String userIdIsAlreadyTaken) {
        super(userIdIsAlreadyTaken);
    }
}
