package com.best.practice.BestPractice.handlers;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class ErrorAttribute extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);

        Throwable throwable = getError(webRequest);
        if (throwable != null) {
            Throwable cause = throwable.getCause();
            if (cause != null) {
                Map<String, Object> causeErrorAttributes = new HashMap<>();
                causeErrorAttributes.put("exception", cause.getClass().getName());
                causeErrorAttributes.put("message", cause.getMessage());
                errorAttributes.put("cause", causeErrorAttributes);
            }
        }
        return errorAttributes;
    }
}
