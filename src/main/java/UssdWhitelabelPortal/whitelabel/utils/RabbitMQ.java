package UssdWhitelabelPortal.whitelabel.utils;

import UssdWhitelabelPortal.whitelabel.configuration.ConfigService;
import UssdWhitelabelPortal.whitelabel.entities.Institution;
import UssdWhitelabelPortal.whitelabel.exceptions.PortalException;
import UssdWhitelabelPortal.whitelabel.repositories.InstitutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class RabbitMQ {

    @Autowired
    RabbitMqProducer producer;

    @Autowired
    private InstitutionRepository institutionRepo;

    @Autowired
    private ConfigService configService;

    @Value("${mybanq.institution.code}")
    private String mybanqEmailCode;

    @Value("${email.enabled}")
    private Boolean isEmailEnabled;

    public RabbitMQ() {
    }

    public void sendSMS(String phoneNumber, String message, String institutionCode, String custNo, String accountNo) {
        Map<String, String> m = new HashMap<>(3);
        m.put("reference", formatPhoneNumber(phoneNumber));
        m.put("message", message);
        m.put("institutionId", institutionCode);
        m.put("custNo", custNo);
        m.put("accountNumber", accountNo);
        String jsonMessage = toJsonString(m);
        producer.produceMessage("SMS_NOTIFICATION_XCHANGE", "SMS_NOTIFICATION", jsonMessage);
    }

    public void sendEmail(String recipient, String title, String message, String institutionCode) {
        Optional<Institution> institution = institutionRepo.findByCode(institutionCode);
        if (institution.isEmpty()) {
            throw new PortalException("Institution not found, code: " + institutionCode);
        }
        Map<String, String> m = new HashMap<>(4);
        m.put("reference", this.formatEmailList(recipient));
        m.put("institutionId", configService.getEmailInstitutionId(institutionCode));
        m.put("title", title);
        m.put("message", message);
        String jsonMessage = toJsonString(m);
        log.info("SENDING EMAIL: {}", jsonMessage);
        if (!isEmailEnabled) {
            return;
        }
        producer.produceMessage("EMAIL_NOTIFICATION_XCHANGE", "EMAIL_NOTIFICATION", jsonMessage);
    }

    protected String toJsonString(Map<String, String> obj) {
        StringBuilder jsonString = new StringBuilder();
        jsonString.append("{");
        for (String key : obj.keySet()) {
            jsonString.append("\"" + key + "\": \"" + obj.get(key) + "\", ");
        }
        jsonString.deleteCharAt(jsonString.lastIndexOf(","));
        jsonString.append("}");
        log.info("Queue Message Body - {}", jsonString.toString());
        return jsonString.toString();
    }

    protected String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("+2340")) {
            return "+234" + phoneNumber.substring(5);
        } else if (phoneNumber.startsWith("0")) {
            return "+234" + phoneNumber.substring(1);
        } else if (phoneNumber.startsWith("234")) {
            return "+234" + phoneNumber.substring(3);
        } else {
            return phoneNumber;
        }
    }

    protected String formatEmailList(String emails) {
        if (emails.startsWith("[")) {
            emails = emails.replace("\"", "\\\"");
            return emails;
        }
        return emails;
    }

}
