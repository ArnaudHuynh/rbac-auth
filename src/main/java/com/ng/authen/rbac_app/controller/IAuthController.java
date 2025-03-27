package com.ng.authen.rbac_app.controller;

import com.ng.authen.rbac_app.model.request.LoginRequest;
import com.ng.authen.rbac_app.model.request.RegisterRequest;
import com.ng.authen.rbac_app.model.response.BaseResponse;
import com.ng.authen.rbac_app.model.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ng.authen.rbac_app.constant.RbacAppConstant.*;

/**
 * The interface Auth controller.
 */
@RequestMapping(AUTH)
public interface IAuthController {

    /**
     * Register response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping(REGISTER)
    @Operation(summary = "Register a new user", description = "Registers a new user with the specified username, password, and email. Assigns the USER role by default.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Username or email already taken")
    })
    ResponseEntity<BaseResponse<String>> register(@RequestBody RegisterRequest request);

    /**
     * Login response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping(LOGIN)
    @Operation(summary = "Log in a user", description = "Authenticates a user and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, JWT token returned"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody LoginRequest request);

    /**
     * Logout response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping(LOGOUT)
    @Operation(summary = "Log out a user", description = "Invalidates the user's JWT token by adding it to a blacklist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged out successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or missing token")
    })
    ResponseEntity<?> logout(HttpServletRequest request);
}
