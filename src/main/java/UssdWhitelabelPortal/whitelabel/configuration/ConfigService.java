package UssdWhitelabelPortal.whitelabel.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ConfigService {

    @Autowired
    private Environment env;


    // give another name
    private String mybanqEmailCode;

    public String getEmailInstitutionId(String institutionCode) {
        String key = institutionCode + ".email.hasSMTP";
        if (Boolean.parseBoolean(env.getProperty(key, String.valueOf(false)))) {
            return institutionCode;
        }
        return mybanqEmailCode;
    }

}
