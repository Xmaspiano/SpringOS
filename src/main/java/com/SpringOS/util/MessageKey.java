package com.SpringOS.util;

import org.springframework.context.NoSuchMessageException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by AlbertXmas on 16/8/25.
 */
public class MessageKey {

    public static String getMsg(String key){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        RequestContext requestContext = new RequestContext(request);
        try {
            return requestContext.getMessage(key);
        }catch (NoSuchMessageException nsme){
            return requestContext.getMessage("60000");
        }
    }
}
