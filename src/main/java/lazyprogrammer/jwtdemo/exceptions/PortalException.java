package lazyprogrammer.jwtdemo.exceptions;

public class PortalException extends RuntimeException {
    public PortalException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public PortalException(String message) {
        super(message);
    }
}
