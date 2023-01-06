package cn.yznu.common.config;

import cn.yznu.common.utils.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.session.web.http.DefaultCookieSerializer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class MyDefaultCookieSerializer extends DefaultCookieSerializer {

    private static final Log logger = LogFactory.getLog(MyDefaultCookieSerializer.class);
    private String cookieName = "SESSION";
    private static final String AUTHORIZATION = "X-Token";
    private boolean useBase64Encoding = true;
    private String jvmRoute;

    @Override
    public List<String> readCookieValues(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        List<String> matchingCookieValues = new ArrayList();
        String token = request.getHeader(this.AUTHORIZATION);
        if(StringUtil.isNotEmpty(token)) {
            String sessionId = this.base64Decode(token);
            if (sessionId != null) {
                if (this.jvmRoute != null && sessionId.endsWith(this.jvmRoute)) {
                    sessionId = sessionId.substring(0, sessionId.length() - this.jvmRoute.length());
                }

                matchingCookieValues.add(sessionId);
            }
        }else if (cookies != null) {
            Cookie[] var4 = cookies;
            int var5 = cookies.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Cookie cookie = var4[var6];
                if (this.cookieName.equals(cookie.getName())) {
                    String sessionId = this.useBase64Encoding ? this.base64Decode(cookie.getValue()) : cookie.getValue();
                    if (sessionId != null) {
                        if (this.jvmRoute != null && sessionId.endsWith(this.jvmRoute)) {
                            sessionId = sessionId.substring(0, sessionId.length() - this.jvmRoute.length());
                        }

                        matchingCookieValues.add(sessionId);
                    }
                }
            }
        }


        return matchingCookieValues;
    }

    private String base64Decode(String base64Value) {
        try {
            byte[] decodedCookieBytes = Base64.getDecoder().decode(base64Value);
            return new String(decodedCookieBytes);
        } catch (Exception var3) {
            logger.debug("Unable to Base64 decode value: " + base64Value);
            return null;
        }
    }

    public void setJvmRoute(String jvmRoute) {
        this.jvmRoute = "." + jvmRoute;
    }
}
