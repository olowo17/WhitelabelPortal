package UssdWhitelabelPortal.whitelabel.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordResponse {

    private String actionMessage;

    private String initiatedDate;

}