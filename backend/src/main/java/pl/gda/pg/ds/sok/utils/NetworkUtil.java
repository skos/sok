package pl.gda.pg.ds.sok.utils;

import javax.servlet.http.HttpServletRequest;

public class NetworkUtil {
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }
}
