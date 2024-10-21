package lazyprogrammer.jwtdemo.infrastructure.context;

import jakarta.servlet.http.HttpServletRequest;
import lazyprogrammer.jwtdemo.utils.GeneratorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContextService {

    private static final String LANGUAGE_CODE_FIELD = "languageCode";
    public static String SOURCE_HEADER_CTX_ID_LOCATION = "ctxId";
    private String SERVICE_PREFIX = "ISW-PRTL";
    @Autowired
    private HttpServletRequest servletRequest;

    public Context getContextForHttpRequest() {

        String traceID = servletRequest.getHeader(SOURCE_HEADER_CTX_ID_LOCATION) != null
                ? servletRequest.getHeader(SOURCE_HEADER_CTX_ID_LOCATION)
                : GeneratorHelper.generateTraceContextId(SERVICE_PREFIX);

        servletRequest.getServletContext().setAttribute(SOURCE_HEADER_CTX_ID_LOCATION, traceID);

        String ipAddress = servletRequest.getHeader("X-FORWARDED-FOR");

        if (ipAddress == null) {

            ipAddress = servletRequest.getRemoteAddr();

        }

        Context ctx = Context.builder()
                .traceID(traceID)
                .service(SERVICE_PREFIX)
                .eventPath(servletRequest.getRequestURI())
                .sourceIpAddress(ipAddress)
                .build();

        if (servletRequest.getHeader(LANGUAGE_CODE_FIELD) != null) {

            ctx.setLanguageCode(servletRequest.getHeader(LANGUAGE_CODE_FIELD));

        }

        return ctx;

    }

    public String getContextID() {

        String traceID = (String) servletRequest.getServletContext().getAttribute(SOURCE_HEADER_CTX_ID_LOCATION);

        if (traceID == null) {

            traceID = GeneratorHelper.generateTraceContextId(SERVICE_PREFIX);

            servletRequest.getServletContext().setAttribute(SOURCE_HEADER_CTX_ID_LOCATION, traceID);

        }

        return traceID;

    }

}
