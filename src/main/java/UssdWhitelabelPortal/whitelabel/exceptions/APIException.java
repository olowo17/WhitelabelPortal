package UssdWhitelabelPortal.whitelabel.exceptions;

import UssdWhitelabelPortal.whitelabel.infrastructure.context.Context;
import UssdWhitelabelPortal.whitelabel.utils.GeneratorHelper;
import UssdWhitelabelPortal.whitelabel.vo.APIExceptionResponse;
import UssdWhitelabelPortal.whitelabel.vo.ServiceResponse;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class APIException extends RuntimeException {
    private final int code = ServiceResponse.ERROR;
    private final String timeStamp = GeneratorHelper.currentDateTimeToString();
    private HttpStatusCode statusCode;
    private List<Object> fields;
    private String message;
    private String traceID;
    private Object error;
    private int status;

    public static APIException contextualError(Context ctx, HttpStatus httpStatus, String message) {
        return APIException.builder()
                .traceID(ctx.getTraceID())
                .message(message)
                .statusCode(httpStatus)
                .build();
    }
    public static APIException apiExceptionError(HttpStatus httpStatus, String message) {
        return APIException.builder()
                .message(message)
                .statusCode(httpStatus)
                .build();
    }

    public static APIExceptionResponse mapToDTO(APIException apiException) {
        return APIExceptionResponse.builder()
                .description(apiException.getMessage())
                .code(apiException.getCode())
                .statusCode(apiException.getStatusCode().value())
                .error(apiException.getError())
                .fields(apiException.getFields())
                .traceID(apiException.getTraceID())
                .build();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
