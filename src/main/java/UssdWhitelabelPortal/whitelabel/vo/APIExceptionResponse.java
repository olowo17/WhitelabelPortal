package UssdWhitelabelPortal.whitelabel.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class APIExceptionResponse extends ServiceResponse {

    private Object error;

    private String traceID;

    private int code;

    private String description;

    private List<Object> fields;

    private int statusCode;

}
