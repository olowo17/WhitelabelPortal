package UssdWhitelabelPortal.whitelabel.utils;

import UssdWhitelabelPortal.whitelabel.vo.LanguageCode;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleHandler {
    private LocaleHandler() {

    }

    public static String getMessage(String languageCode, String messageKey) {
        Locale currentLocale;
        ResourceBundle messages;
        currentLocale = new Locale(StringUtils.isEmpty(languageCode) ? LanguageCode.ENGLISH : languageCode);
        messages = ResourceBundle.getBundle("messages", currentLocale);
        return messages.getString(messageKey);
    }
}
