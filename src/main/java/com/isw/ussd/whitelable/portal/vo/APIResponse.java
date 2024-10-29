package com.isw.ussd.whitelable.portal.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse<DataType> extends ServiceResponse {

    private DataType data;

    private String traceID;

    private int code;

    private HttpStatus statusCode = HttpStatus.OK;

    private String description;

    public static <DataType> APIResponse<DataType> Ok(DataType data, String traceID, String message) {

        APIResponse<DataType> response = (APIResponse<DataType>) APIResponse.builder()
                .data(data)
                .traceID(traceID)
                .code(ServiceResponse.SUCCESS)
                .description(message)
                .build();


        return response;

    }

    public int getStatusCode() {

        if (statusCode == null) {

            statusCode = HttpStatus.OK;

        }

        return statusCode.value();

    }


}
