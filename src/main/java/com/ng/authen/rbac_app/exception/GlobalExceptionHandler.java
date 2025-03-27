package com.ng.authen.rbac_app.exception;

import com.ng.authen.rbac_app.model.response.CustomErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomErrorResponse> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CustomErrorResponse> handleUnauthorizedException(UnauthorizedException ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                        .message("Access denied: You do not have permission to access this resource")
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .message("Authentication failed: " + ex.getMessage())
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<CustomErrorResponse> handleJwtException(JwtException ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .message("Invalid JWT token: " + ex.getMessage())
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .message("An unexpected error occurred: " + ex.getMessage())
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}