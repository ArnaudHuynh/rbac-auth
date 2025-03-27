package com.ng.authen.rbac_app.controller.impl;


import com.ng.authen.rbac_app.controller.IAuthController;
import com.ng.authen.rbac_app.exception.BadRequestException;
import com.ng.authen.rbac_app.handler.LoginHandler;
import com.ng.authen.rbac_app.handler.RegisterHandler;
import com.ng.authen.rbac_app.model.request.LoginRequest;
import com.ng.authen.rbac_app.model.request.RegisterRequest;
import com.ng.authen.rbac_app.model.response.BaseResponse;
import com.ng.authen.rbac_app.model.response.LoginResponse;
import com.ng.authen.rbac_app.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements IAuthController {

    private final RegisterHandler registerHandler;
    private final LoginHandler loginHandler;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthController(RegisterHandler registerHandler,
                          LoginHandler loginHandler,
                          TokenBlacklistService tokenBlacklistService) {
        this.registerHandler = registerHandler;
        this.loginHandler = loginHandler;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    public ResponseEntity<BaseResponse<String>> register(@RequestBody RegisterRequest request) {

        return ResponseEntity.ok(registerHandler.handle(request));
    }

    public ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(loginHandler.handle(request));
    }

    public ResponseEntity<BaseResponse<String>> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BadRequestException("Invalid or missing token");
        }

        String token = authHeader.substring(7);
        tokenBlacklistService.blacklistToken(token);
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(
                BaseResponse.<String>builder()
                        .requestId(MDC.get("requestId"))
                        .success(true)
                        .data("Logged out successfully")
                        .build()
        );
    }
}