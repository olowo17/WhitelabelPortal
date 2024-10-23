package lazyprogrammer.jwtdemo.params;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lazyprogrammer.jwtdemo.utils.DateHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetTokenInfo {

    private String email;

    private String initiatedTimeStamp;

    private String expiredTimeStamp;

    private String institutionCode;

    @JsonIgnore
    public Boolean isExpired() throws ParseException {
        return new Date().after(DateHelper.parseDate(expiredTimeStamp));
    }

}