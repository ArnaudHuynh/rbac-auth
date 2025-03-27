package com.ng.authen.rbac_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ng.authen.rbac_app.constant.RbacAppConstant.*;

/**
 * The interface Test controller.
 */
@RequestMapping(URI)
public interface ITestController {
    /**
     * Admin endpoint response entity.
     *
     * @return the response entity
     */
    @GetMapping(ADMIN)
    @Operation(summary = "Access admin endpoint", description = "Endpoint accessible only to users with the ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access granted"),
            @ApiResponse(responseCode = "403", description = "Forbidden - ADMIN role required"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required")
    })
    ResponseEntity<String> adminEndpoint();

    /**
     * User endpoint response entity.
     *
     * @return the response entity
     */
    @GetMapping(USER)
    @Operation(summary = "Access user endpoint", description = "Endpoint accessible to users with the USER or ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access granted"),
            @ApiResponse(responseCode = "403", description = "Forbidden - USER or ADMIN role required"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required")
    })
    ResponseEntity<String> userEndpoint();
}
