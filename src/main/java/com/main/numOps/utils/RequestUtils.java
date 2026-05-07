package com.main.numOps.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtils {

    private RequestUtils() {
    }

    public static HttpServletRequest getCurrentRequest() {

        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            return null;
        }

        return attributes.getRequest();
    }

    public static String getCurrentPath() {

        HttpServletRequest request = getCurrentRequest();

        return request != null
                ? request.getRequestURI()
                : null;
    }
}
