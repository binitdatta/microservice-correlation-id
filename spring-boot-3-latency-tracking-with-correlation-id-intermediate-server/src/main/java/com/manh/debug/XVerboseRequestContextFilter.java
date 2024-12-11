package com.manh.debug;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class XVerboseRequestContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("XVerboseRequestContextFilter executed.");

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        boolean isXverbose = "true".equalsIgnoreCase(httpServletRequest.getParameter("xverbose"));
        System.out.println("XVerboseRequestContextFilter executed." + isXverbose);

        // Initialize the RequestEntity
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setXverbose(isXverbose);
        RequestContext.set(requestEntity);

        try {
            chain.doFilter(request, response);
        } finally {
            // Cleanup after the request is processed
            RequestContext.clear();
        }
    }
}
