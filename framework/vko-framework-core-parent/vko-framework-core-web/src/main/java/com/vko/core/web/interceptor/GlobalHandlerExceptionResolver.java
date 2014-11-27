package com.vko.core.web.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.vko.core.common.util.HttpUtil;






/**
 * This is global exception resolver to handle exception thrown by controllers.
 * 
 * @author sunyi
 * 
 * spring.bean id="handlerExceptionResolver"
 */
public class GlobalHandlerExceptionResolver implements HandlerExceptionResolver {

    protected static final Logger logger = LoggerFactory.getLogger(GlobalHandlerExceptionResolver.class); 

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String url = HttpUtil.getURL(request);
        logger.info("Exception from URL: " + url);
        logger.info("Exception: " + ex.getMessage());
        Map<String, String> map = new HashMap<String, String>();
        String message = ex.getMessage();
        map.put("url", url);
        map.put("message", message==null ? "(未知错误)" : message);
       /* if(SecurityUtil.isAdminRole()) {
            StringBuffer sb = new StringBuffer(1024);
            for(StackTraceElement ste : ex.getStackTrace()) {
                sb.append("at ").append(ste.getClassName())
                    .append('.').append(ste.getMethodName());
                String file = ste.getFileName();
                if(file!=null) {
                    int line = ste.getLineNumber();
                    if(line>0)
                        sb.append("(" + file + ":" + line + ")");
                    else 
                        sb.append("(" + file + ")");
                }
                sb.append("<br />");
            }
            map.put("class", ex.getClass().getName());
            map.put("stack", sb.toString());
        }*/
        return new ModelAndView("/error.html", map);
    }

}
