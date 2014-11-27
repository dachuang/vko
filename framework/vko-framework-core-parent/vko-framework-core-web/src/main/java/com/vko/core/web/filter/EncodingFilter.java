package com.vko.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Set request's character encoding. The default encoding is set to UTF-8.
 * 
 * @author darkwing
 */
public class EncodingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(EncodingFilter.class); 

    private String encoding = "UTF-8";

    public void init(FilterConfig filterConfig) throws ServletException {
        String encoding = filterConfig.getInitParameter("encoding");
        if(encoding!=null && !"".equals(encoding.trim()))
            this.encoding = encoding.trim();
        logger.info("Request character encoding is set to " + encoding);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    public void destroy() {
    	logger.info("EncodingFilter destroyed.");
    }

}
