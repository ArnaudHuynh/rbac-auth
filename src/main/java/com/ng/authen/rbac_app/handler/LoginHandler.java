package com.ng.authen.rbac_app.handler;

import com.ng.authen.rbac_app.model.request.LoginRequest;
import com.ng.authen.rbac_app.model.response.BaseResponse;
import com.ng.authen.rbac_app.model.response.LoginResponse;
import com.ng.authen.rbac_app.util.JwtUtil;
import org.slf4j.MDC;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * The type Login handler.
 */
@Component
public class LoginHandler implements HandlerWrapper<LoginRequest, BaseResponse<LoginResponse>> {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /**
     * Instantiates a new Login handler.
     *
     * @param authenticationManager the authentication manager
     * @param jwtUtil               the jwt util
     */
    public LoginHandler(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public BaseResponse<LoginResponse> handle(LoginRequest request) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String token = jwtUtil.generateToken(request.getUsername());

        return BaseResponse.<LoginResponse>builder()
                .requestId(MDC.get("requestId"))
                .success(true)
                .data(
                        LoginResponse.builder()
                                .token(token)
                                .build()
                )
                .build();
    }
}
