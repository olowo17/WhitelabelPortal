package lazyprogrammer.jwtdemo.vo;

public class ServiceResponse {
    public static final int SUCCESS = 0;
    public static final int ERROR = 10;
    public static final int SESSION_INVALID = 30;
    public static final String GENERAL_ERROR_MESSAGE = "Request Processing Error";
    public static final String GENERAL_SUCCESS_MESSAGE = "Operation Successful";

    private int code;

    private String description;

    public ServiceResponse() {

    }

    public ServiceResponse(int code) {
        this.code = code;
    }

    public ServiceResponse(int code, String description) {
        this(code);
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
