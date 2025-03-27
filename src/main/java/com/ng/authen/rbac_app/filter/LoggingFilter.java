package com.ng.authen.rbac_app.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component
public class LoggingFilter extends OncePerRequestFilter implements Ordered {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    private static final int FILTER_ORDER = -150;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Wrap the request and response to cache their content
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        // Log the incoming request
        logRequest(wrappedRequest);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            // Log the outgoing response
            logResponse(wrappedResponse);
            // Copy the cached content to the original response
            wrappedResponse.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) throws IOException {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String fullUrl = queryString != null ? uri + "?" + queryString : uri;

        // Log headers
        StringBuilder headers = new StringBuilder();
        Collections.list(request.getHeaderNames()).forEach(headerName ->
                headers.append(headerName).append(": ").append(request.getHeader(headerName)).append("; ")
        );

        // Log body (if present)
        String body = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);

        logger.info("Incoming Request: method={}, url={}, headers=[{}], body={}", method, fullUrl, headers, body);
    }

    private void logResponse(ContentCachingResponseWrapper response) throws IOException {
        int status = response.getStatus();
        String body = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);

        // Log headers
        StringBuilder headers = new StringBuilder();
        response.getHeaderNames().forEach(headerName ->
                headers.append(headerName).append(": ").append(response.getHeader(headerName)).append("; ")
        );

        logger.info("Outgoing Response: status={}, headers=[{}], body={}", status, headers, body);
    }

    @Override
    public int getOrder() {
        return FILTER_ORDER;
    }
}