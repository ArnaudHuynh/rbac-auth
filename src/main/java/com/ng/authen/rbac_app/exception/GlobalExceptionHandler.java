package com.ng.authen.rbac_app.exception;

import com.ng.authen.rbac_app.model.response.CustomErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type Global exception handler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle resource not found exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .requestId(MDC.get("requestId"))
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Handle bad request exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomErrorResponse> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .requestId(MDC.get("requestId"))
                        .build()
                , HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle unauthorized exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CustomErrorResponse> handleUnauthorizedException(UnauthorizedException ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .requestId(MDC.get("requestId"))
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle access denied exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                        .message("Access denied: You do not have permission to access this resource")
                        .path(request.getRequestURI())
                        .requestId(MDC.get("requestId"))
                        .build(),
                HttpStatus.FORBIDDEN);
    }

    /**
     * Handle authentication exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .message("Authentication failed: " + ex.getMessage())
                        .path(request.getRequestURI())
                        .requestId(MDC.get("requestId"))
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle jwt exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<CustomErrorResponse> handleJwtException(JwtException ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .message("Invalid JWT token: " + ex.getMessage())
                        .path(request.getRequestURI())
                        .requestId(MDC.get("requestId"))
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle generic exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .message("An unexpected error occurred: " + ex.getMessage())
                        .path(request.getRequestURI())
                        .requestId(MDC.get("requestId"))
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}