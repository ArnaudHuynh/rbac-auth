package com.ng.authen.rbac_app.controller;

import com.ng.authen.rbac_app.model.SidebarItem;
import com.ng.authen.rbac_app.model.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.ng.authen.rbac_app.constant.RbacAppConstant.MENU;
import static com.ng.authen.rbac_app.constant.RbacAppConstant.SIDEBAR;

/**
 * The interface Menu controller.
 */
@RequestMapping(MENU)
public interface IMenuController {

    /**
     * Gets side bar.
     *
     * @return the side bar
     */
    @GetMapping(SIDEBAR)
    @Operation(summary = "Get menu items", description = "Returns a list of menu items based on the authenticated user's roles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu items retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required")
    })
    ResponseEntity<BaseResponse<List<SidebarItem>>> getSideBar();
}
